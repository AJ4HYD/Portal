package portal.domain.eb5.edi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DTMMessageSegment{
	private String PeriodQualifier;
	private Date Period;
	private int PeriodFormatQualifier;
	DateFormat df = null;
	
	public DTMMessageSegment(String periodQualifier, int periodFormatQualifier, Date period) {
		PeriodQualifier = periodQualifier;
		PeriodFormatQualifier = periodFormatQualifier;
		Period = period;
		
		switch (PeriodFormatQualifier){
		case 102:	df = new SimpleDateFormat("YYYYMMdd"); break;
		case 602:	df = new SimpleDateFormat("YYYY"); break;
	}
	}
	public DTMMessageSegment(String periodQualifier, int periodFormatQualifier, String period) throws ParseException {
		PeriodQualifier = periodQualifier;
		PeriodFormatQualifier = periodFormatQualifier;
		
		switch (PeriodFormatQualifier){
		case 102:	df = new SimpleDateFormat("YYYYMMdd"); break;
		case 602:	df = new SimpleDateFormat("YYYY"); break;
	}
		
		try{
			Period = df.parse(period);	
		}
		catch (NullPointerException e){
			Period = new Date();
		}
		catch (ParseException e){
			Period = new Date();
		}
	}
	public String generate() {
		if (Period != null){
			MessageSegement.messageSegmentCount++;
				
			return "DTM"
				+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
				+ PeriodQualifier
				+ UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR
				+ "<mark>"+df.format(Period)+"</mark>"
				+ UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR
				+ PeriodFormatQualifier
				+ UNAServiceStringAdvice.SEGMENT_TERMINATOR;
		}
		else{
			return "";
		}
	}
}