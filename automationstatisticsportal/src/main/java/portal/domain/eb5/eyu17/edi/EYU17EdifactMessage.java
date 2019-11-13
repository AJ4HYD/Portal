package portal.domain.eb5.eyu17.edi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import portal.domain.eb5.edi.ALCMessageSegment;
import portal.domain.eb5.edi.ATTMessageSegment;
import portal.domain.eb5.edi.DTMMessageSegment;
import portal.domain.eb5.edi.EMPMessageSegment;
import portal.domain.eb5.edi.MessageSegement;
import portal.domain.eb5.edi.NADMessageSegment;
import portal.domain.eb5.edi.TAXMOAMessageSegments;
import portal.domain.eb5.edi.UNAServiceStringAdvice;
import portal.domain.eb5.edi.UNHMessageHeader;
import portal.domain.eb5.fps18.edi.EdifactMessage;

public class EYU17EdifactMessage extends EdifactMessage {
	// OPTIONAL FIELDS
	protected String addressLine1 = "1 CARDBOARD LANE";
	protected String addressLine2 = "TELFORD";
	protected String addressLine3 = "SHROPSHIRE";
	protected String addressLine4;
	protected String foreignCountry;
	protected String forename;
	protected String secondForename;
	protected String title;
	protected String ukPostCode = "TL9 9ZZ";
	
	// MANDATORY FIELDS
	protected String employerPAYEReference;
	protected String employerAccountsOfficeRef;
	protected String HMRCOfficeNumber;
	protected Date messgeDate = new Date();
	protected Date relatedTaxYear;
	protected String surname;
	protected String NINO;
	protected String Gender = "F";
	protected String dateOfBirth;
	protected String employerName;

	protected String taxablePayinThisPayPeriodIncludingPayrolledBenefitsInKind;
	protected String specialServiceTaxCode;

	public EYU17EdifactMessage() throws ParseException{
		MessageSegement.messageSegmentCount = 2;
		relatedTaxYear = new SimpleDateFormat("yyyy").parse("2017");
	}
	
	public String generate() throws ParseException {
		return
			new UNHMessageHeader("29","EYU17").generate()
			+ new NADMessageSegment("SD").generate()
			+ new NADMessageSegment("BG", employerName).generate()
			+ new ATTMessageSegment("7", "2", employerPAYEReference).generate()
			+ new ATTMessageSegment("103", "2", employerAccountsOfficeRef).generate()
			+ new NADMessageSegment("TC").generate()
			+ new ATTMessageSegment("17", "2", HMRCOfficeNumber).generate()
			+ new DTMMessageSegment("243", 102, messgeDate).generate()
			+ new DTMMessageSegment("166", 602, relatedTaxYear).generate()
			+ "UNS" + UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR + "D" + UNAServiceStringAdvice.SEGMENT_TERMINATOR
			+ "LIN" + UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR + "1" + UNAServiceStringAdvice.SEGMENT_TERMINATOR
			+ new NADMessageSegment("BV", addressLine1, addressLine2, addressLine3, addressLine4, foreignCountry, surname, 
					forename, secondForename, title, ukPostCode).generate()
			+ new ATTMessageSegment("11", "2", NINO).generate()
			+ new ATTMessageSegment("9", "2", Gender).generate()
			+ new DTMMessageSegment("329", 102, dateOfBirth).generate()
			+ new EMPMessageSegment().generate()
			+ new TAXMOAMessageSegments("153", "10", taxablePayinThisPayPeriodIncludingPayrolledBenefitsInKind).generate()
			+ new ALCMessageSegment("4", "1", specialServiceTaxCode).generate()
			+ "UNS" + UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR + "S" + UNAServiceStringAdvice.SEGMENT_TERMINATOR
			+ "CNT" + UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR + "2" + UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR + "1" + UNAServiceStringAdvice.SEGMENT_TERMINATOR
			+ "UNT" + UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR + MessageSegement.messageSegmentCount + UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR + "1" + UNAServiceStringAdvice.SEGMENT_TERMINATOR
			+ "";
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getAddressLine4() {
		return addressLine4;
	}

	public void setAddressLine4(String addressLine4) {
		this.addressLine4 = addressLine4;
	}

	public String getForeignCountry() {
		return foreignCountry;
	}

	public void setForeignCountry(String foreignCountry) {
		this.foreignCountry = foreignCountry;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSecondForename() {
		return secondForename;
	}

	public void setSecondForename(String secondForename) {
		this.secondForename = secondForename;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUkPostCode() {
		return ukPostCode;
	}

	public void setUkPostCode(String ukPostCode) {
		this.ukPostCode = ukPostCode;
	}

	public String getEmployerPAYEReference() {
		return employerPAYEReference;
	}

	public void setEmployerPAYEReference(String employerPAYEReference) {
		this.employerPAYEReference = employerPAYEReference;
	}

	public String getEmployerAccountsOfficeRef() {
		return employerAccountsOfficeRef;
	}

	public void setEmployerAccountsOfficeRef(String employerAccountsOfficeRef) {
		this.employerAccountsOfficeRef = employerAccountsOfficeRef;
	}

	public String getHMRCOfficeNumber() {
		return HMRCOfficeNumber;
	}

	public void setHMRCOfficeNumber(String hMRCOfficeNumber) {
		HMRCOfficeNumber = hMRCOfficeNumber;
	}

	public Date getMessgeDate() {
		return messgeDate;
	}

	public void setMessgeDate(Date messgeDate) {
		this.messgeDate = messgeDate;
	}

	public Date getRelatedTaxYear() {
		return relatedTaxYear;
	}

	public void setRelatedTaxYear(Date relatedTaxYear) {
		this.relatedTaxYear = relatedTaxYear;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getNINO() {
		return NINO;
	}

	public void setNINO(String nINO) {
		NINO = nINO;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public String getTaxablePayinThisPayPeriodIncludingPayrolledBenefitsInKind() {
		return taxablePayinThisPayPeriodIncludingPayrolledBenefitsInKind;
	}

	public void setTaxablePayinThisPayPeriodIncludingPayrolledBenefitsInKind(
			String taxablePayinThisPayPeriodIncludingPayrolledBenefitsInKind) {
		this.taxablePayinThisPayPeriodIncludingPayrolledBenefitsInKind = taxablePayinThisPayPeriodIncludingPayrolledBenefitsInKind;
	}

	public String getSpecialServiceTaxCode() {
		return specialServiceTaxCode;
	}

	public void setSpecialServiceTaxCode(String specialServiceTaxCode) {
		this.specialServiceTaxCode = specialServiceTaxCode;
	}
	
	
	
}