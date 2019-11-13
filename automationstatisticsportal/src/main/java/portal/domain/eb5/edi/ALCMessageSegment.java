package portal.domain.eb5.edi;

public class ALCMessageSegment{
	private static final String ALLOWANCE_QUALIFIER = "ZZZ";
	private static final String CODE_LIST_AGENCY = "IR";
	private String SpecialServicesCoded = "4";
	private String CodeListQualifier = "1";
	private String SpecialServiceTaxCode;
	
	public ALCMessageSegment(String specialServicesCoded, String codeListQualifier, String specialServiceTaxCode) {
		SpecialServicesCoded = specialServicesCoded;
		CodeListQualifier = codeListQualifier;
		SpecialServiceTaxCode = specialServiceTaxCode;
	}

	public String generate() {
		MessageSegement.messageSegmentCount++;
		
		return "ALC"
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ ALLOWANCE_QUALIFIER
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ SpecialServicesCoded
			+ UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR
			+ CodeListQualifier
			+ UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR
			+ CODE_LIST_AGENCY
			+ UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR
			+ SpecialServiceTaxCode
			+ UNAServiceStringAdvice.SEGMENT_TERMINATOR;
	}
}