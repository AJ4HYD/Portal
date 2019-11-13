package portal.domain.eb5.edi;

public class EMPMessageSegment{
	private static final String EMPLOYMENT_QUALIFIER = "ZZZ";
	private static final String EMPLOYMENT_CATEGORY = "ED";
	private static final String CODE_LIST_AGENCY = "IR";
	private static final String CodeListQualifier = "6";
	
	public String generate() {
		MessageSegement.messageSegmentCount++;
		
		return "EMP"
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ EMPLOYMENT_QUALIFIER
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ EMPLOYMENT_CATEGORY
			+ UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR
			+ CodeListQualifier
			+ UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR
			+ CODE_LIST_AGENCY
			+ UNAServiceStringAdvice.SEGMENT_TERMINATOR;
	}
}
