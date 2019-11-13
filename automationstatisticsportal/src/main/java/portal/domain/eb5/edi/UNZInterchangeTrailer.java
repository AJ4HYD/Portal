package portal.domain.eb5.edi;

public class UNZInterchangeTrailer {

	public String generate() {
		return "UNZ" + UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR + "1" 
				+ UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR + "1" + UNAServiceStringAdvice.SEGMENT_TERMINATOR;
	}
}