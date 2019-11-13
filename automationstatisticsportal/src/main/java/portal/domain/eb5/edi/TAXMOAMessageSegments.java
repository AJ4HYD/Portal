package portal.domain.eb5.edi;

public class TAXMOAMessageSegments{
	private static final String MONETARY_AMOUNT_QUALIFIER = "ZZZ";
	private static final String TAX_DETAILS_CODE = "9";
	private static final String CODE_LIST_AGENCY = "IR";
	private String TaxTypeCode;
	private String CodeListQualifier;
	private String AttributeAmountPenceGBP;
	
	public TAXMOAMessageSegments(String taxTypeCode, String codeListQualifier, String attributeMonetaryAmount) {;
		TaxTypeCode = taxTypeCode;
		CodeListQualifier = codeListQualifier;
		AttributeAmountPenceGBP = attributeMonetaryAmount;
	}

	public String generate() {
		// turn pound to pence except for type 181
		String endstop = "00</mark>";
		if (TaxTypeCode == "181")
			endstop = "</mark>";

		if (AttributeAmountPenceGBP != null && AttributeAmountPenceGBP != ""){
			MessageSegement.messageSegmentCount++;
			MessageSegement.messageSegmentCount++;
			return "TAX"
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ TAX_DETAILS_CODE
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ TaxTypeCode
			+ UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR
			+ CodeListQualifier
			+ UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR
			+ CODE_LIST_AGENCY
			+ UNAServiceStringAdvice.SEGMENT_TERMINATOR
			+ "MOA"
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ MONETARY_AMOUNT_QUALIFIER
			+ UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR
			+ "<mark>"+AttributeAmountPenceGBP+endstop
			+ UNAServiceStringAdvice.SEGMENT_TERMINATOR;
		}
		else
			return "";
	}
}
