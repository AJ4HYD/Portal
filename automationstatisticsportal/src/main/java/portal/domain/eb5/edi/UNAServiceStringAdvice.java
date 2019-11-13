package portal.domain.eb5.edi;

public final class UNAServiceStringAdvice {

	public final static String SEGMENT_TERMINATOR = "'\r\n";
	public final static String SEGMENT_ELEMENT_SEPARATOR = "+";
	public final static String COMPONENT_ELEMENT_SEPARATOR = ":";
	public final static String DECIMAL_POINT = ".";
	public final static String RELEASE_CHARACTER= "?";
	
	public String generate() {
		return "UNA"
			+ COMPONENT_ELEMENT_SEPARATOR
			+ SEGMENT_ELEMENT_SEPARATOR
			+ DECIMAL_POINT
			+ RELEASE_CHARACTER
			+ " "
			+ SEGMENT_TERMINATOR;
	}
}