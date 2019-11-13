package portal.domain.eb5.fps18.edi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import portal.domain.eb5.edi.ALCMessageSegment;
import portal.domain.eb5.edi.ATTMessageSegment;
import portal.domain.eb5.edi.DTMMessageSegment;
import portal.domain.eb5.edi.EMPMessageSegment;
import portal.domain.eb5.edi.FTXMessageSegment;
import portal.domain.eb5.edi.MessageSegement;
import portal.domain.eb5.edi.NADMessageSegment;
import portal.domain.eb5.edi.TAXMOAMessageSegments;
import portal.domain.eb5.edi.UNAServiceStringAdvice;
import portal.domain.eb5.edi.UNHMessageHeader;

public class EdifactMessage {
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
	protected String senderId;
	protected String employerPAYEReference;
	protected String employerAccountsOfficeRef;
	protected String SAUTR;
	protected String cotaxRef;
	protected String HMRCOfficeNumber;
	protected Date messgeDate = new Date();
	protected Date relatedTaxYear;
	protected String surname;
	protected String NINO;
	protected String Gender = "F";
	protected String dateOfBirth;
	protected String employerName;
	protected String paymentDate;
	protected String payrollId;
	protected String payFrequency;
	protected String paymentWeeklyPeriodNumber;
	//protected String paymentMonthlyPeriodNumber; // GOING TO USE SINGLE FIELD FOR PERIOD DETAIL
	protected String numberOfPeriodsCoveredByStatement;
	protected String numberOfNormalHoursWorked;
	protected String taxablePayToDate;
	protected String totalTaxToDate;
	protected String studentLoandsRepaymentsRecoveredToDate;
	protected String taxablePayinThisPayPeriodIncludingPayrolledBenefitsInKind;
	protected String nonTaxOrNICPayment;
	protected String deductionsfromNetPayinthisPayPeriod;
	protected String payafterStatutoryDeductions;
	protected String benefitsTaxedviathePayroll;
	protected String EmployeePensionContributionsPaidUnderNetPayArrangementsinPayPeriod;
	protected String ItemsSubjecttoClass1NIC;
	protected String employeeContributionsnotPaid;
	protected String StudentLoanRepaymentRecoveredthisPeriod;
	protected String taxDeductedorRefundedFromThisPayment;
	protected String statutoryMaternityPaySMPYeartoDate;
	protected String StatutoryAdoptionPaySAPYeartoDate;
	protected String StatutoryPaternityPaySPPYeartoDate;
	protected String sharedParentalPayShPPYeartoDate;
	protected String benefitsTaxedviathePayrollYeartoDate;
	protected String employeePensionContributionsPaidUndernetpayarrangementsYeartoDate;
	protected String employeePensionContributionsNotPaidUndernetpayarrangementsYeartoDate;
	protected String flexibleDrawdownTaxablePayment;
	protected String flexibleDrawdownNonTaxablePayment;
	protected String specialServiceTaxCode;
	// change these car fields to List type in future for multiple records
	protected ArrayList<String> carMakeModel;
	protected ArrayList<String> carCO2EmissionsFigure;
	protected ArrayList<String> carFuelType;
	protected ArrayList<String> carIdentifier;
	protected ArrayList<String> carAmendmentIndicator;
	protected ArrayList<String> carDateCarWasAvailableFrom;
	protected ArrayList<String> carDateCarWasAvailableTo;
	protected ArrayList<String> carDateFreeFuelProvided;
	protected ArrayList<String> carDateFreeFuelWasWithdrawn;
	protected ArrayList<String> carCalculatedPricePence;
	protected ArrayList<String> carCashEquivalentofCarPence;
	protected ArrayList<String> carCashEquivalentofFuelPence;
	protected String header;

	public EdifactMessage() throws ParseException{
		MessageSegement.messageSegmentCount = 2;
		relatedTaxYear = new SimpleDateFormat("yyyy").parse("2018");
		header = new UNHMessageHeader("24","FPS18").generate();
	}
	
	public String generate() throws ParseException {
		String payPeriodDetails = "";
		if (payFrequency.subSequence(0, 1)=="W"){
			payPeriodDetails = new ATTMessageSegment("211", "2", paymentWeeklyPeriodNumber).generate();
		}
		if (payFrequency.subSequence(0, 1)=="M"){
			payPeriodDetails = new ATTMessageSegment("212", "2", paymentWeeklyPeriodNumber).generate();
		}
		return
			header
			+ new NADMessageSegment("SD").generate()
			+ new NADMessageSegment("BG", employerName).generate()
			// ATT Vendor ID 
			// ATT Developers Name
			// ATT Payroll Product Name
			// ATT Payroll Product Version 
			// NAD Employer Name 
			+ new ATTMessageSegment("7", "2", employerPAYEReference).generate()
			+ new ATTMessageSegment("103", "2", employerAccountsOfficeRef).generate()
			+ new ATTMessageSegment("71", "2", SAUTR).generate()
			+ new ATTMessageSegment("72", "2", cotaxRef).generate()
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
			// + new ATTMessageSegment("201", "2", PassportNumber).generate()
			+ new DTMMessageSegment("329", 102, dateOfBirth).generate()
			// + new NADMessageSegment("EP", null, null, null, null, null, surname, forename, secondForename, null, null).generate()
			// + ATT Partner NINO
			+ new EMPMessageSegment().generate()
			// + DTM 330/102 starting date CCYYMMDD 
			// + DTM 337/102 date leaving employment CCYYMMDD 
			+ new DTMMessageSegment("350", 102, paymentDate).generate()
			//+ new ATTMessageSegment("5", "2", startingDeclaration).generate()
			+ new ATTMessageSegment("19", "2", payrollId).generate()
			// + ATT 207 
			// + ATT 18
			// + ATT 207
			// + ATT 209 
			+ new ATTMessageSegment("210", "2", payFrequency).generate()
			+ payPeriodDetails
			+ new ATTMessageSegment("213", "2", numberOfPeriodsCoveredByStatement).generate()
			+ new ATTMessageSegment("216", "2", numberOfNormalHoursWorked).generate()
			+ new TAXMOAMessageSegments("80", "10", taxablePayToDate).generate()
			+ new TAXMOAMessageSegments("82", "10", totalTaxToDate).generate()
			+ new TAXMOAMessageSegments("93", "10", studentLoandsRepaymentsRecoveredToDate).generate()
			+ new TAXMOAMessageSegments("153", "10", taxablePayinThisPayPeriodIncludingPayrolledBenefitsInKind).generate()
			+ new TAXMOAMessageSegments("156", "10", nonTaxOrNICPayment).generate()
			+ new TAXMOAMessageSegments("157", "10", deductionsfromNetPayinthisPayPeriod).generate()
			+ new TAXMOAMessageSegments("158", "10", payafterStatutoryDeductions).generate()
			+ new TAXMOAMessageSegments("159", "10", benefitsTaxedviathePayroll).generate()
			+ new TAXMOAMessageSegments("162", "10", EmployeePensionContributionsPaidUnderNetPayArrangementsinPayPeriod).generate()
			+ new TAXMOAMessageSegments("170", "10", ItemsSubjecttoClass1NIC).generate()
			+ new TAXMOAMessageSegments("163", "10", employeeContributionsnotPaid).generate()
			+ new TAXMOAMessageSegments("164", "10", StudentLoanRepaymentRecoveredthisPeriod).generate()
			+ new TAXMOAMessageSegments("165", "10", taxDeductedorRefundedFromThisPayment).generate()
			+ new TAXMOAMessageSegments("65", "10",  statutoryMaternityPaySMPYeartoDate).generate()
			+ new TAXMOAMessageSegments("126", "10", StatutoryPaternityPaySPPYeartoDate).generate()
			+ new TAXMOAMessageSegments("127", "10", StatutoryAdoptionPaySAPYeartoDate).generate()
			+ new TAXMOAMessageSegments("136", "10", sharedParentalPayShPPYeartoDate).generate()
			+ new TAXMOAMessageSegments("174", "10", benefitsTaxedviathePayrollYeartoDate).generate()
			+ new TAXMOAMessageSegments("175", "10", employeePensionContributionsPaidUndernetpayarrangementsYeartoDate).generate()
			+ new TAXMOAMessageSegments("176", "10", employeePensionContributionsNotPaidUndernetpayarrangementsYeartoDate).generate()
			+ new TAXMOAMessageSegments("178", "10", flexibleDrawdownTaxablePayment).generate()
			+ new TAXMOAMessageSegments("179", "10", flexibleDrawdownNonTaxablePayment).generate()
			// skip  Employee Trivial Commutation Payments 
			+ generateCarData()
			+ new ALCMessageSegment("4", "1", specialServiceTaxCode).generate()
			+ "UNS" + UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR + "S" + UNAServiceStringAdvice.SEGMENT_TERMINATOR
			+ "CNT" + UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR + "2" + UNAServiceStringAdvice.COMPONENT_ELEMENT_SEPARATOR + "1" + UNAServiceStringAdvice.SEGMENT_TERMINATOR
			+ "UNT" + UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR + MessageSegement.messageSegmentCount + UNAServiceStringAdvice.SEGMENT_ELEMENT_SEPARATOR + "1" + UNAServiceStringAdvice.SEGMENT_TERMINATOR
			+ "";
	}
	
	private String generateCarData() throws ParseException{
		// method proper
		String carData = "";
		if (carIdentifier != null){
			//optional fields
			while (carDateCarWasAvailableTo.size()<carIdentifier.size())
				carDateCarWasAvailableTo.add("");
			while (carDateFreeFuelProvided.size()<carIdentifier.size())
				carDateFreeFuelProvided.add("");
			while (carDateFreeFuelWasWithdrawn.size()<carIdentifier.size())
				carDateFreeFuelWasWithdrawn.add("");
			while (carCashEquivalentofFuelPence.size()<carIdentifier.size())
				carCashEquivalentofFuelPence.add("");
			
			int carIndex = 0;
			for (String car : carIdentifier){
				if ( car.length()>0 && carIndex < 15){
					carData += new FTXMessageSegment("8", "F").generate()
							+  new ATTMessageSegment("246", "4", carMakeModel.get(carIndex)).generate()
							+  new ATTMessageSegment("247", "4", carCO2EmissionsFigure.get(carIndex)).generate()
							+  new ATTMessageSegment("248", "4", carFuelType.get(carIndex)).generate()
							+  new ATTMessageSegment("249", "4", car).generate()
							+  new ATTMessageSegment("250", "4", carAmendmentIndicator.get(carIndex)).generate()
							+  new DTMMessageSegment("376", 102, carDateCarWasAvailableFrom.get(carIndex)).generate()
							+  new DTMMessageSegment("377", 102, carDateCarWasAvailableTo.get(carIndex)).generate()
							+  new DTMMessageSegment("378", 102, carDateFreeFuelProvided.get(carIndex)).generate()
							+  new DTMMessageSegment("376", 102, carDateFreeFuelWasWithdrawn.get(carIndex)).generate()
							+  new TAXMOAMessageSegments("181", "10", carCalculatedPricePence.get(carIndex)).generate()
							+  new TAXMOAMessageSegments("181", "10", carCashEquivalentofCarPence.get(carIndex)).generate()
							+  new TAXMOAMessageSegments("181", "10", carCashEquivalentofFuelPence.get(carIndex)).generate();
				}
				carIndex++;
			}
		}
		return carData;
	}
	
	
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public void setAddresLine1(String addresLine1) {
		this.addressLine1 = addresLine1;
	}
	public void setAddresLine2(String addresLine2) {
		this.addressLine2 = addresLine2;
	}
	public void setAddresLine3(String addresLine3) {
		this.addressLine3 = addresLine3;
	}
	public void setAddresLine4(String addresLine4) {
		this.addressLine4 = addresLine4;
	}
	public void setForeignCountry(String foreignCountry) {
		this.foreignCountry = foreignCountry;
	}
	public void setForename(String forename) {
		this.forename = forename;
	}
	public void setSecondForename(String secondForename) {
		this.secondForename = secondForename;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setUkPostCode(String ukPostCode) {
		this.ukPostCode = ukPostCode;
	}
	public void setEmployerPAYEReference(String employerPAYEReference) {
		this.employerPAYEReference = employerPAYEReference;
	}
	public void setEmployerAccountsOfficeRef(String employerAccountsOfficeRef) {
		this.employerAccountsOfficeRef = employerAccountsOfficeRef;
	}
	public void setSAUTR(String sAUTR) {
		SAUTR = sAUTR;
	}
	public void setCotaxRef(String cotaxRef) {
		this.cotaxRef = cotaxRef;
	}
	public void setHMRCOfficeNumber(String hMRCOfficeNumber) {
		HMRCOfficeNumber = hMRCOfficeNumber;
	}
	public void setMessgeDate(Date messgeDate) {
		this.messgeDate = messgeDate;
	}
	public void setRelatedTaxYear(Date relatedTaxYear) {
		this.relatedTaxYear = relatedTaxYear;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public void setNINO(String nINO) {
		NINO = nINO;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public void setPayrollId(String payrollId) {
		this.payrollId = payrollId;
	}
	public void setPayFrequency(String payFrequency) {
		this.payFrequency = payFrequency;
	}
	public void setPaymentWeeklyPeriodNumber(String paymentWeeklyPeriodNumber) {
		this.paymentWeeklyPeriodNumber = paymentWeeklyPeriodNumber;
	}
	public void setNumberOfPeriodsCoveredByStatement(String numberOfPeriodsCoveredByStatement) {
		this.numberOfPeriodsCoveredByStatement = numberOfPeriodsCoveredByStatement;
	}
	public void setNumberOfNormalHoursWorked(String numberOfNormalHoursWorked) {
		this.numberOfNormalHoursWorked = numberOfNormalHoursWorked;
	}
	public void setTaxablePayToDate(String taxablePayToDate) {
		this.taxablePayToDate = taxablePayToDate;
	}
	public void setTotalTaxToDate(String totalTaxToDate) {
		this.totalTaxToDate = totalTaxToDate;
	}
	public void setStudentLoandsRepaymentsRecoveredToDate(String studentLoandsRepaymentsRecoveredToDate) {
		this.studentLoandsRepaymentsRecoveredToDate = studentLoandsRepaymentsRecoveredToDate;
	}
	public void setTaxablePayinThisPayPeriodIncludingPayrolledBenefitsInKind(
			String taxablePayinThisPayPeriodIncludingPayrolledBenefitsInKind) {
		this.taxablePayinThisPayPeriodIncludingPayrolledBenefitsInKind = taxablePayinThisPayPeriodIncludingPayrolledBenefitsInKind;
	}
	public void setNonTaxOrNICPayment(String nonTaxOrNICPayment) {
		this.nonTaxOrNICPayment = nonTaxOrNICPayment;
	}
	public void setDeductionsfromNetPayinthisPayPeriod(String deductionsfromNetPayinthisPayPeriod) {
		this.deductionsfromNetPayinthisPayPeriod = deductionsfromNetPayinthisPayPeriod;
	}
	public void setPayafterStatutoryDeductions(String payafterStatutoryDeductions) {
		this.payafterStatutoryDeductions = payafterStatutoryDeductions;
	}
	public void setBenefitsTaxedviathePayroll(String benefitsTaxedviathePayroll) {
		this.benefitsTaxedviathePayroll = benefitsTaxedviathePayroll;
	}
	public void setEmployeePensionContributionsPaidUnderNetPayArrangementsinPayPeriod(
			String employeePensionContributionsPaidUnderNetPayArrangementsinPayPeriod) {
		EmployeePensionContributionsPaidUnderNetPayArrangementsinPayPeriod = employeePensionContributionsPaidUnderNetPayArrangementsinPayPeriod;
	}
	public void setItemsSubjecttoClass1NIC(String itemsSubjecttoClass1NIC) {
		ItemsSubjecttoClass1NIC = itemsSubjecttoClass1NIC;
	}
	public void setEmployeeContributionsnotPaid(String employeeContributionsnotPaid) {
		this.employeeContributionsnotPaid = employeeContributionsnotPaid;
	}
	public void setStudentLoanRepaymentRecoveredthisPeriod(String studentLoanRepaymentRecoveredthisPeriod) {
		StudentLoanRepaymentRecoveredthisPeriod = studentLoanRepaymentRecoveredthisPeriod;
	}
	public void setTaxDeductedorRefundedFromThisPayment(String taxDeductedorRefundedFromThisPayment) {
		this.taxDeductedorRefundedFromThisPayment = taxDeductedorRefundedFromThisPayment;
	}
	public void setStatutoryMaternityPaySMPYeartoDate(String statutoryMaternityPaySMPYeartoDate) {
		this.statutoryMaternityPaySMPYeartoDate = statutoryMaternityPaySMPYeartoDate;
	}
	public void setStatutoryAdoptionPaySAPYeartoDate(String statutoryAdoptionPaySAPYeartoDate) {
		StatutoryAdoptionPaySAPYeartoDate = statutoryAdoptionPaySAPYeartoDate;
	}
	public void setStatutoryPaternityPaySPPYeartoDate(String statutoryPaternityPaySPPYeartoDate) {
		StatutoryPaternityPaySPPYeartoDate = statutoryPaternityPaySPPYeartoDate;
	}
	public void setSharedParentalPayShPPYeartoDate(String sharedParentalPayShPPYeartoDate) {
		this.sharedParentalPayShPPYeartoDate = sharedParentalPayShPPYeartoDate;
	}
	public void setBenefitsTaxedviathePayrollYeartoDate(String benefitsTaxedviathePayrollYeartoDate) {
		this.benefitsTaxedviathePayrollYeartoDate = benefitsTaxedviathePayrollYeartoDate;
	}
	public void setEmployeePensionContributionsPaidUndernetpayarrangementsYeartoDate(
			String employeePensionContributionsPaidUndernetpayarrangementsYeartoDate) {
		this.employeePensionContributionsPaidUndernetpayarrangementsYeartoDate = employeePensionContributionsPaidUndernetpayarrangementsYeartoDate;
	}
	public void setEmployeePensionContributionsNotPaidUndernetpayarrangementsYeartoDate(
			String employeePensionContributionsNotPaidUndernetpayarrangementsYeartoDate) {
		this.employeePensionContributionsNotPaidUndernetpayarrangementsYeartoDate = employeePensionContributionsNotPaidUndernetpayarrangementsYeartoDate;
	}
	public void setFlexibleDrawdownTaxablePayment(String flexibleDrawdownTaxablePayment) {
		this.flexibleDrawdownTaxablePayment = flexibleDrawdownTaxablePayment;
	}
	public void setFlexibleDrawdownNonTaxablePayment(String flexibleDrawdownNonTaxablePayment) {
		this.flexibleDrawdownNonTaxablePayment = flexibleDrawdownNonTaxablePayment;
	}
	public void setCarMakeModel(ArrayList<String> carMakeModel) {
		this.carMakeModel = carMakeModel;
	}
	public void setCarCO2EmissionsFigure(ArrayList<String> carCO2EmissionsFigure) {
		this.carCO2EmissionsFigure = carCO2EmissionsFigure;
	}
	public void setCarFuelType(ArrayList<String> carFuelType) {
		this.carFuelType = carFuelType;
	}
	public void setCarIdentifier(ArrayList<String>carIdentifier) {
		this.carIdentifier = carIdentifier;
	}
	public void setCarAmendmentIndicator(ArrayList<String> carAmendmentIndicator) {
		this.carAmendmentIndicator = carAmendmentIndicator;
	}
	public void setCarDateCarWasAvailableFrom(ArrayList<String> carDateCarWasAvailableFrom) {
		this.carDateCarWasAvailableFrom = carDateCarWasAvailableFrom;
	}
	public void setCarDateCarWasAvailableTo(ArrayList<String> carDateCarWasAvailableTo) {
		this.carDateCarWasAvailableTo = carDateCarWasAvailableTo;
	}
	public void setCarDateFreeFuelProvided(ArrayList<String>carDateFreeFuelProvided) {
		this.carDateFreeFuelProvided = carDateFreeFuelProvided;
	}
	public void setCarDateFreeFuelWasWithdrawn(ArrayList<String> carDateFreeFuelWasWithdrawn) {
		this.carDateFreeFuelWasWithdrawn = carDateFreeFuelWasWithdrawn;
	}
	public void setCarCalculatedPricePence(ArrayList<String>carCalculatedPricePence) {
		this.carCalculatedPricePence = carCalculatedPricePence;
	}
	public void setCarCashEquivalentofCarPence(ArrayList<String> carCashEquivalentofCarPence) {
		this.carCashEquivalentofCarPence = carCashEquivalentofCarPence;
	}
	public void setCarCashEquivalentofFuelPence(ArrayList<String> carCashEquivalentofFuelPence) {
		this.carCashEquivalentofFuelPence = carCashEquivalentofFuelPence;
	}
	public void setSpecialServiceTaxCode(String specialServiceTaxCode) {
		this.specialServiceTaxCode = specialServiceTaxCode;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public String getAddressLine4() {
		return addressLine4;
	}

	public String getForeignCountry() {
		return foreignCountry;
	}

	public String getForename() {
		return forename;
	}

	public String getSecondForename() {
		return secondForename;
	}

	public String getTitle() {
		return title;
	}

	public String getUkPostCode() {
		return ukPostCode;
	}

	public String getEmployerPAYEReference() {
		return employerPAYEReference;
	}

	public String getEmployerAccountsOfficeRef() {
		return employerAccountsOfficeRef;
	}

	public String getSAUTR() {
		return SAUTR;
	}

	public String getCotaxRef() {
		return cotaxRef;
	}

	public String getHMRCOfficeNumber() {
		return HMRCOfficeNumber;
	}

	public Date getMessgeDate() {
		return messgeDate;
	}

	public Date getRelatedTaxYear() {
		return relatedTaxYear;
	}

	public String getSurname() {
		return surname;
	}

	public String getNINO() {
		return NINO;
	}

	public String getGender() {
		return Gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getEmployerName() {
		return employerName;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public String getPayrollId() {
		return payrollId;
	}

	public String getPayFrequency() {
		return payFrequency;
	}

	public String getPaymentWeeklyPeriodNumber() {
		return paymentWeeklyPeriodNumber;
	}

	public String getNumberOfPeriodsCoveredByStatement() {
		return numberOfPeriodsCoveredByStatement;
	}

	public String getNumberOfNormalHoursWorked() {
		return numberOfNormalHoursWorked;
	}

	public String getTaxablePayToDate() {
		return taxablePayToDate;
	}

	public String getTotalTaxToDate() {
		return totalTaxToDate;
	}

	public String getStudentLoandsRepaymentsRecoveredToDate() {
		return studentLoandsRepaymentsRecoveredToDate;
	}

	public String getTaxablePayinThisPayPeriodIncludingPayrolledBenefitsInKind() {
		return taxablePayinThisPayPeriodIncludingPayrolledBenefitsInKind;
	}

	public String getNonTaxOrNICPayment() {
		return nonTaxOrNICPayment;
	}

	public String getDeductionsfromNetPayinthisPayPeriod() {
		return deductionsfromNetPayinthisPayPeriod;
	}

	public String getPayafterStatutoryDeductions() {
		return payafterStatutoryDeductions;
	}

	public String getBenefitsTaxedviathePayroll() {
		return benefitsTaxedviathePayroll;
	}

	public String getEmployeePensionContributionsPaidUnderNetPayArrangementsinPayPeriod() {
		return EmployeePensionContributionsPaidUnderNetPayArrangementsinPayPeriod;
	}

	public String getItemsSubjecttoClass1NIC() {
		return ItemsSubjecttoClass1NIC;
	}

	public String getEmployeeContributionsnotPaid() {
		return employeeContributionsnotPaid;
	}

	public String getStudentLoanRepaymentRecoveredthisPeriod() {
		return StudentLoanRepaymentRecoveredthisPeriod;
	}

	public String getTaxDeductedorRefundedFromThisPayment() {
		return taxDeductedorRefundedFromThisPayment;
	}

	public String getStatutoryMaternityPaySMPYeartoDate() {
		return statutoryMaternityPaySMPYeartoDate;
	}

	public String getStatutoryAdoptionPaySAPYeartoDate() {
		return StatutoryAdoptionPaySAPYeartoDate;
	}

	public String getStatutoryPaternityPaySPPYeartoDate() {
		return StatutoryPaternityPaySPPYeartoDate;
	}

	public String getSharedParentalPayShPPYeartoDate() {
		return sharedParentalPayShPPYeartoDate;
	}

	public String getBenefitsTaxedviathePayrollYeartoDate() {
		return benefitsTaxedviathePayrollYeartoDate;
	}

	public String getEmployeePensionContributionsPaidUndernetpayarrangementsYeartoDate() {
		return employeePensionContributionsPaidUndernetpayarrangementsYeartoDate;
	}

	public String getEmployeePensionContributionsNotPaidUndernetpayarrangementsYeartoDate() {
		return employeePensionContributionsNotPaidUndernetpayarrangementsYeartoDate;
	}

	public String getFlexibleDrawdownTaxablePayment() {
		return flexibleDrawdownTaxablePayment;
	}

	public String getFlexibleDrawdownNonTaxablePayment() {
		return flexibleDrawdownNonTaxablePayment;
	}

	public ArrayList<String> getCarMakeModel() {
		return carMakeModel;
	}
	public ArrayList<String> getCarCO2EmissionsFigure() {
		return carCO2EmissionsFigure;
	}
	public ArrayList<String> getCarFuelType() {
		return carFuelType;
	}
	public ArrayList<String> getCarIdentifier() {
		return carIdentifier;
	}
	public ArrayList<String> getCarAmendmentIndicator() {
		return carAmendmentIndicator;
	}
	public ArrayList<String> getCarDateCarWasAvailableFrom() {
		return carDateCarWasAvailableFrom;
	}
	public ArrayList<String> getCarDateCarWasAvailableTo() {
		return carDateCarWasAvailableTo;
	}
	public ArrayList<String> getCarDateFreeFuelProvided() {
		return carDateFreeFuelProvided;
	}
	public ArrayList<String> getCarDateFreeFuelWasWithdrawn() {
		return carDateFreeFuelWasWithdrawn;
	}
	public ArrayList<String> getCarCalculatedPricePence() {
		return carCalculatedPricePence;
	}
	public ArrayList<String> getCarCashEquivalentofCarPence() {
		return carCashEquivalentofCarPence;
	}
	public ArrayList<String> getCarCashEquivalentofFuelPence() {
		return carCashEquivalentofFuelPence;
	}
	public String getSpecialServiceTaxCode() {
		return specialServiceTaxCode;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
}