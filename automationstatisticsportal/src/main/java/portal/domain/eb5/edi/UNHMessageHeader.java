package portal.domain.eb5.edi;

public class UNHMessageHeader{
	private static final String MESSAGE_VERSION = "1";
	private static final String MESSAGE_RELEASE = "0";
	private static final String CODE_LIST_AGENCY;
	private static final String CONTROLLING_AGENCY = CODE_LIST_AGENCY = "IR";
	private static final String CODE_LIST_QUALIFIER = "5";
	private int count = 0;
	// fps18=24, eyu17=29
	private String DOCUMENT_NAME_CODE = "24";
	private String messageType = "FPS18";
	
	public UNHMessageHeader(String dOCUMENT_NAME_CODE, String messageType) {
		this.DOCUMENT_NAME_CODE = dOCUMENT_NAME_CODE;
		this.messageType=messageType;
	}

	public String generate() {
		++count;
		// increment segment count once for UNH and once for BGM
		MessageSegement.messageSegmentCount++;
		MessageSegement.messageSegmentCount++;
		return "UNH"
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ count
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ messageType
			+ UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR
			+ MESSAGE_VERSION
			+ UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR
			+ MESSAGE_RELEASE
			+ UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR
			+ CONTROLLING_AGENCY
			+ UNAServiceStringAdvice.SEGMENT_TERMINATOR
			+ "BGM"
			+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR
			+ DOCUMENT_NAME_CODE
			+ UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR
			+ CODE_LIST_QUALIFIER
			+ UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR
			+ CODE_LIST_AGENCY
			+ UNAServiceStringAdvice.SEGMENT_TERMINATOR;
	}
}