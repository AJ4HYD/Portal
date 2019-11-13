package portal.controllers;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import portal.domain.TestRun;
import portal.domain.TestSuite;
import portal.domain.eb5.edi.EdifactInterchange;
import portal.domain.eb5.eyu17.edi.EYU17EdifactMessage;
import portal.domain.eb5.fps18.edi.EdifactMessage;
import portal.repositories.RecordTypeRepository;
import portal.repositories.TestCaseRepository;
import portal.repositories.TestSuiteRepository;
import portal.repositories.TestToolRepository;
import portal.services.TestRunServiceImpl;

@Controller
public class EdifactController {
	@Autowired
    private TestRunServiceImpl testRunService;
	@Autowired
    private TestCaseRepository testCaseRepository;
	@Autowired
    private TestToolRepository testToolRepository;
	@Autowired
    private TestSuiteRepository testSuiteRepository;
	@Autowired
    private RecordTypeRepository recordTypeRepository;
	
    @GetMapping("/portal/eb5/fps18/edi")
    public String fps18Form(Model model) throws ParseException {
        model.addAttribute("fps18_edi", new EdifactMessage());
        return "fps18-edi";
    }
    @PostMapping("/portal/eb5/fps18/edi")
    public String fps18Submit(Model model, @ModelAttribute EdifactMessage message) throws ParseException {
    	Date startTime =new Date();
    	
    	EdifactInterchange fpsFile = new EdifactInterchange("FPS18");
    	fpsFile.setMessage(message);
    	fpsFile.setInterchangeDate(new Date());
    	fpsFile.setInterchangeTime(new Date());
    	fpsFile.setSenderId(message.getSenderId());
    	model.addAttribute("fps18_edi_result", fpsFile.generateFile().replace("\r\n", "<br/>"));
    	
    	// create matching EYU file pair
    	EdifactInterchange eyuFile = new EdifactInterchange("EYU17");
    	EYU17EdifactMessage eyuMessage = new EYU17EdifactMessage();
    	eyuMessage.setAddressLine1(message.getAddressLine1());
    	eyuMessage.setAddressLine2(message.getAddressLine2());
    	eyuMessage.setAddressLine3(message.getAddressLine3());
    	eyuMessage.setAddressLine4(message.getAddressLine4());
    	eyuMessage.setDateOfBirth(message.getDateOfBirth());
    	eyuMessage.setEmployerAccountsOfficeRef(message.getEmployeeContributionsnotPaid());
    	eyuMessage.setEmployerName(message.getEmployerName());
    	eyuMessage.setEmployerPAYEReference(message.getEmployerPAYEReference());
    	eyuMessage.setForeignCountry(message.getForeignCountry());
    	eyuMessage.setForename(message.getForename());
    	eyuMessage.setGender(message.getGender());
    	eyuMessage.setHMRCOfficeNumber(message.getHMRCOfficeNumber());
    	eyuMessage.setMessgeDate(message.getMessgeDate());
    	eyuMessage.setNINO(message.getNINO());
    	eyuMessage.setRelatedTaxYear(message.getRelatedTaxYear());
    	eyuMessage.setSecondForename(message.getSecondForename());
    	eyuMessage.setSpecialServiceTaxCode(message.getSpecialServiceTaxCode());
    	eyuMessage.setSurname(message.getSurname());
    	eyuMessage.setTaxablePayinThisPayPeriodIncludingPayrolledBenefitsInKind(message.getTaxablePayinThisPayPeriodIncludingPayrolledBenefitsInKind());
    	eyuMessage.setTitle(message.getTitle());
    	eyuMessage.setUkPostCode(message.getUkPostCode());
    	
    	eyuFile.setMessage(eyuMessage);
    	eyuFile.setInterchangeDate(new Date());
    	eyuFile.setInterchangeTime(new Date());
    	eyuFile.setSenderId(message.getSenderId());
    	model.addAttribute("eyu17_edi_result", eyuFile.generateFile().replace("\r\n", "<br/>"));
    	
    	// log fps file to portal
    	TestRun r = new TestRun();
    	TestSuite s = testSuiteRepository.findByTestToolAndTestCase(testToolRepository.findByName("Automation Portal EB5 Generators"),
    			testCaseRepository.findByName("EB5 FPS18 EDI Generator"));
    	System.out.println(s.getTestCase().getId()+" "+
    			s.getTestTool().getId());
    	r.setTestSuite(s);
    	r.setMessage("Excerpt of the data generated (first 200 characters):\r\n"
    			+ fpsFile.generateFile().replace("<mark>", "").replace("</mark>", "").substring(0, 198));
    	r.setResult(true);
    	r.setUsername("Portal User");
    	r.setStartDateTime(startTime);
    	r.setEndDateTime(new Date());
    	r.setGeneratedDataCount((long)1);
    	r.setGeneratedDataType(recordTypeRepository.findOne((long)6));
    	testRunService.add(r);
    	// log eyu file to portal
    	r = new TestRun();
    	r.setId(null);
    	s = testSuiteRepository.findByTestToolAndTestCase(testToolRepository.findByName("Automation Portal EB5 Generators"),
    			testCaseRepository.findByName("EB5 EYU17 EDI Generator"));
    	r.setTestSuite(s);
    	r.setMessage("Excerpt of the data generated (first 200 characters):\r\n"
    			+ eyuFile.generateFile().replace("<mark>", "").replace("</mark>", "").substring(0, 198));
    	r.setResult(true);
    	r.setUsername("Portal User");
    	r.setStartDateTime(startTime);
    	r.setEndDateTime(new Date());
    	r.setGeneratedDataCount((long)1);
    	r.setGeneratedDataType(recordTypeRepository.findOne((long)6));
    	testRunService.add(r);
    	
        return "fps18-edi-result";
    }
    
    @GetMapping("/portal/eb5/eyu17/edi")
    public String eyu17Form(Model model) throws ParseException {
        model.addAttribute("eyu17_edi", new EYU17EdifactMessage());
        return "eyu17-edi";
    }
    @PostMapping("/portal/eb5/eyu17/edi")
    public String eyu17Submit(Model model, @ModelAttribute EYU17EdifactMessage message) throws ParseException {
    	EdifactInterchange file = new EdifactInterchange("EYU17");
    	file.setMessage(message);
    	file.setInterchangeDate(new Date());
    	file.setInterchangeTime(new Date());
    	file.setSenderId(message.getSenderId());
    	model.addAttribute("eyu17_edi_result", file.generateFile().replace("\r\n", "<br/>"));
        return "eyu17-edi-result";
    }
}