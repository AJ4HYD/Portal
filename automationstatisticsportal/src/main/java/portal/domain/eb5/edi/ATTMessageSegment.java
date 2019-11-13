package portal.domain.eb5.edi;

public class ATTMessageSegment{
	private static final String MESSAGEATTRIBUTE_FUNCTION_QUALIFIER = "ZZZ";
	private static final String CODE_LIST_AGENCY = "IR";
	private String AttributeCode;
	private String CodeListQualifier;
	private String Attribute;
	
	public ATTMessageSegment(String attributeCode, String codeListQualifier, String attribute) {
		AttributeCode = attributeCode;
		CodeListQualifier = codeListQualifier;
		Attribute = attribute;   
	}
	
	public String generate() {
		if (Attribute != null && Attribute != ""){
			MessageSegement.messageSegmentCount++;
			return "ATT"
				+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
				+ MESSAGEATTRIBUTE_FUNCTION_QUALIFIER
				+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
				+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
				+ AttributeCode
				+ UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR
				+ CodeListQualifier
				+ UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR
				+ CODE_LIST_AGENCY
				+ UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR
				+ "<mark>"+Attribute+"</mark>"
				+ UNAServiceStringAdvice.SEGMENT_TERMINATOR;
		}
		else
			return "";
	}
}
