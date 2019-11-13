package portal.domain.eb5.edi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UNBInterchangeHeader {

	private static final String SYNTAX_IDENTIFIER = "UNOC";
	private static final String SYNTAX_VERSION = "3";
	private static final String RECEIVER_ID = "HMRC";
	
	private static final DateFormat df = new SimpleDateFormat("yyMMdd"); 
	private static final DateFormat tf = new SimpleDateFormat("HHmm"); 
	private int count = 0;

	public String senderId;
	public Date interchangeDate;
	public Date interchangeTime;
	public String ApplicationReference = "FPS18";
	
	public UNBInterchangeHeader(String senderId, Date interchangeDate, Date interchangeTime, String applicationReference) {
		this.senderId = senderId;
		this.interchangeDate = interchangeDate;
		this.interchangeTime = interchangeTime;
		this.ApplicationReference = applicationReference;
	}

	public String generate() {
		++count;
		return "UNB"
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ SYNTAX_IDENTIFIER
			+ UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR
			+ SYNTAX_VERSION
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ "<mark>"+senderId+"</mark>"
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ RECEIVER_ID
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ "<mark>"+df.format(interchangeDate)+"</mark>"
			+ UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR
			+ "<mark>"+tf.format(interchangeTime)+"</mark>"
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ count
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ ApplicationReference
			+ UNAServiceStringAdvice.SEGMENT_TERMINATOR;
	}
}