package portal.domain.eb5;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import portal.domain.eb5.edi.ATTMessageSegment;
import portal.domain.eb5.edi.DTMMessageSegment;
import portal.domain.eb5.edi.FTXMessageSegment;
import portal.domain.eb5.edi.NADMessageSegment;
import portal.domain.eb5.edi.TAXMOAMessageSegments;
import portal.domain.eb5.edi.UNBInterchangeHeader;
import portal.domain.eb5.edi.UNHMessageHeader;

public class Edifact {

	@Test
	public void UNBInterchangeHeader() throws ParseException {
		assertEquals("UNB+UNOC:3+SENDERID+HMRC+170414:0935+1++FPS18'\r\n", new UNBInterchangeHeader("SENDERID", 
				new SimpleDateFormat("yyyy/MM/dd").parse("2017/04/14"), 
				new SimpleDateFormat("HH:mm").parse("09:35"),"FPS18").generate().replace("<mark>", "").replace("</mark>", ""));
	}
	@Test
	public void UNHMessageHeader() throws ParseException {
		assertEquals("UNH+1+FPS18:1:0:IR'\r\nBGM+24:5:IR'\r\n", new UNHMessageHeader("24","FPS18").generate());
	}
	@Test
	public void NADMessageSegment_SD() throws ParseException {
		assertEquals("NAD+SD'\r\n", new NADMessageSegment("SD").generate());
	}
	@Test
	public void NADMessageSegment_BG() throws ParseException {
		assertEquals("NAD+BG+++EMPLOYER NAME'\r\n", new NADMessageSegment("BG", "EMPLOYER NAME")
				.generate().replace("<mark>", "").replace("</mark>", ""));
	}
	@Test
	public void NADMessageSegment_TC() throws ParseException {
		assertEquals("NAD+TC'\r\n", new NADMessageSegment("TC").generate());
	}
	@Test
	public void NADMessageSegment_BV() throws ParseException {
		assertEquals("NAD+BV++1 CARDBOARD LANE:TELFORD:SHROPSHIRE+THYME:JUSTIN:EDWARD:MR++++TL9 9ZZ'\r\n", 
				new NADMessageSegment("BV", "1 CARDBOARD LANE", "TELFORD", "SHROPSHIRE", "", "", "THYME", "JUSTIN",
						"EDWARD", "MR", "TL9 9ZZ").generate().replace("<mark>", "").replace("</mark>", ""));
	}
	@Test
	public void ATTMessageSegment_49() throws ParseException {
		assertEquals("ATT+ZZZ++49:2:IR:1'\r\n", new ATTMessageSegment("49", "2", "1")
				.generate().replace("<mark>", "").replace("</mark>", ""));
	}
	@Test
	public void ATTMessageSegment_2246() throws ParseException {
		assertEquals("ATT+ZZZ++246:4:IR:FORD MONDEO'\r\n", new ATTMessageSegment("246", "4", "FORD MONDEO")
				.generate().replace("<mark>", "").replace("</mark>", ""));
	}
	@Test
	public void DTMMessageSegment_DoB() throws ParseException {
		assertEquals("DTM+329:19751025:102'\r\n", new DTMMessageSegment("329", 102, 
				new SimpleDateFormat("yyyy/MM/dd").parse("1975/10/25"))
				.generate().replace("<mark>", "").replace("</mark>", ""));
	}
	@Test
	public void TAXMOAMessageSegment() throws ParseException {
		assertEquals("TAX+9+80:10:IR'\r\nMOA+ZZZ:61200'\r\n", new TAXMOAMessageSegments("80", "10", "612").generate().replace("<mark>", "").replace("</mark>", ""));
	}
	@Test
	public void FTXMessageSegment() throws ParseException {
		assertEquals("FTX+ZZZ++F:8:IR'\r\n", new FTXMessageSegment("8", "F").generate());
	}
	@Test
	public void EdifactMessage() throws ParseException {
		portal.domain.eb5.fps18.edi.EdifactMessage m = new portal.domain.eb5.fps18.edi.EdifactMessage();
		m.setPayFrequency("M1");
		m.setAddresLine1("a");
		m.setAddresLine2("a");
		m.setAddresLine3("a");
		m.setBenefitsTaxedviathePayroll("a");
		m.setCotaxRef("a");
		m.setForename("harold");
		m.setSurname("robson");
		m.setBenefitsTaxedviathePayroll("999");
		m.setEmployerName("employtek");
		m.setGender("m");
		m.setHMRCOfficeNumber("hmrc");
		System.out.println(m.generate());
	}
}
