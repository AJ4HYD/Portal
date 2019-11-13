package portal.domain.eb5.edi;

import java.text.ParseException;
import java.util.Date;

import portal.domain.eb5.fps18.edi.EdifactMessage;

public class EdifactInterchange {
	UNBInterchangeHeader interchangeHeader;
	EdifactMessage message;
	UNZInterchangeTrailer interchangeTrailer = new UNZInterchangeTrailer();
	UNAServiceStringAdvice serviceStringAdvice = new UNAServiceStringAdvice();
	private String senderId;
	private Date interchangeDate;
	private Date interchangeTime;	
	private String applicationReference;
	
	
	public EdifactInterchange(String applicationReference) {
		super();
		this.applicationReference = applicationReference;
	}
	public EdifactMessage getMessage() {
		return message;
	}
	public void setMessage(EdifactMessage message) {
		this.message = message;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public Date getInterchangeDate() {
		return interchangeDate;
	}
	public void setInterchangeDate(Date interchangeDate) {
		this.interchangeDate = interchangeDate;
	}
	public Date getInterchangeTime() {
		return interchangeTime;
	}
	public void setInterchangeTime(Date interchangeTime) {
		this.interchangeTime = interchangeTime;
	}

	public String generateFile() throws ParseException{
		return
			serviceStringAdvice.generate()
			+ new UNBInterchangeHeader(senderId, interchangeDate, interchangeTime,applicationReference).generate()
			+ message.generate()
			+ interchangeTrailer.generate();
	}
}