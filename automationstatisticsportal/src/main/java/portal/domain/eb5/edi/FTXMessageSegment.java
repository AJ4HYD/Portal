package portal.domain.eb5.edi;

public class FTXMessageSegment{
	private static final String TEXT_SUBJECT_QUALIFIER = "ZZZ";
	private static final String CODE_LIST_AGENCY = "IR";
	private String CodeListQualifier;
	private String Attribute;
		
	public FTXMessageSegment(String codeListQualifier, String attribute) {
		CodeListQualifier = codeListQualifier;
		Attribute = attribute;
	}

	public String generate() {
		MessageSegement.messageSegmentCount++;
		
		return "FTX"
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ TEXT_SUBJECT_QUALIFIER
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ Attribute
			+ UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR
			+ CodeListQualifier
			+ UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR
			+ CODE_LIST_AGENCY
			+ UNAServiceStringAdvice.SEGMENT_TERMINATOR;
	}
}