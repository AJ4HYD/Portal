package portal.domain.eb5.edi;

public class NADMessageSegment{

	private String PartyQualifier;
	private String Surname;
	// OPTIONAL FIELDS 
	private String AddresLine1 = "";
	private String AddresLine2 = "";
	private String AddresLine3 = "";
	private String AddresLine4 = "";
	private String ForeignCountry = "";
	private String Forename = "";
	private String SecondForename = "";
	private String Title = "";
	private String UkPostCode = "";
	
	public NADMessageSegment(String partyQualifier) {
		PartyQualifier = partyQualifier;

	}
	public NADMessageSegment(String partyQualifier, String name) {
		PartyQualifier = partyQualifier;
		Forename = name;
	}
	public NADMessageSegment(String partyQualifier, String addresLine1, String addresLine2, String addresLine3,
			String addresLine4, String foreignCountry, String surname, String forename, String secondForename,
			String title, String ukPostCode) {
		PartyQualifier = partyQualifier;
		if (addresLine1 != "" && addresLine1 != null)
			AddresLine1 = String.format("%s%s%s%s", "<mark>", addresLine1, "</mark>", UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR);
		if (addresLine2 != "" && addresLine2 != null)
			AddresLine2 = String.format("%s%s%s%s", "<mark>", addresLine2, "</mark>", UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR);
		if (addresLine3 != "" && addresLine3 != null)
			AddresLine3 = String.format("%s%s%s%s", "<mark>", addresLine3, "</mark>", UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR);
		if (addresLine4 != "" && addresLine4 != null)
			AddresLine4 = String.format("%s%s%s%s", "<mark>", addresLine4, "</mark>", UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR);
		if (foreignCountry != "" && foreignCountry != null)
			ForeignCountry = String.format("%s%s%s%s", "<mark>", foreignCountry, "</mark>", UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR);
		if (surname != "" && surname != null)
			Surname = String.format("%s%s%s%s", "<mark>", surname, "</mark>", UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR);
		if (forename != "" && forename != null)  // also Employer Name when Party Qualifier is BG
			Forename = String.format("%s%s%s%s", "<mark>", forename, "</mark>", UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR);
		if (secondForename != "" && secondForename != null)
			SecondForename = String.format("%s%s%s%s", "<mark>", secondForename, "</mark>", UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR);
		Title = String.format("%s%s%s", "<mark>", title, "</mark>");
		UkPostCode = String.format("%s%s%s", "<mark>", ukPostCode, "</mark>");
	}

	public String generate() {
		MessageSegement.messageSegmentCount++;
		
		String BV = "";
		String EP = "";
		String BG = "";
		
		if (PartyQualifier == "BG"){
			BG = ""
				+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
				+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
				+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
				+ "<mark>"+Forename+"</mark>";
		}
		
		if (PartyQualifier == "BV"){
			if (Surname != null)
				BV = ""
				+ generateAddressSegment()
				+ generateNameSegment()
				+ "<mark>"+Title+"</mark>"
				+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
				+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
				+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
				+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
				+ "<mark>"+UkPostCode+"</mark>";
		}
	
		if (PartyQualifier == "EP"){
			EP = ""
				+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
				+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
				+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
				+ generateNameSegment();
		}
		
		return "NAD" 
				+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
				+ PartyQualifier
				+ BV + EP + BG
				+ UNAServiceStringAdvice.SEGMENT_TERMINATOR;
	}
	
	private String generateAddressSegment(){
		return ""
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ generateAddressComponent()
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR;
	}
	private String generateAddressComponent(){
		String address =  ""
			+ AddresLine1
			+ AddresLine2
			+ AddresLine3
			+ AddresLine4
			+ ForeignCountry;
		if (address.length()> 0)
			address= address.substring(0, address.length()-1);
		return address;
	}
	private String generateNameSegment(){
		return ""
			+ "<mark>"+Surname+"</mark>"
			+ "<mark>"+Forename+"</mark>"
			+ "<mark>"+SecondForename+"</mark>";
	}
}
