package portal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;
import portal.domain.TestCase;
import portal.domain.TestSuite;
import portal.domain.TestTool;
import portal.repositories.TestCaseRepository;
import portal.repositories.TestToolRepository;
import portal.services.TestCaseService;
import portal.services.TestCaseServiceImpl;

import javax.validation.Valid;

@Controller
public class TestCaseController {
    private TestCaseService testCaseService;
    private TestCaseRepository testCaseRepository;
    private TestToolRepository testToolRepository;

    @Autowired
    public void setTestToolRepository(TestToolRepository testToolRepository) {
        this.testToolRepository = testToolRepository;
    }

    @Autowired
    public void setTestCaseRepository(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
    }


    @Autowired
    private TestCaseServiceImpl tcsi;

    @Autowired
    public void setTestRunService(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @RequestMapping(value = "portal/testcases", method = RequestMethod.GET)
    public String listTestCases(Model model) {
        model.addAttribute("testcases", testCaseService.listAllTestCases());
        model.addAttribute("totals", testCaseService.getTotals());
        //model.addAttribute("toolId", testcase.);

        return "testcases";
    }

    @RequestMapping(value = "portal/catalogue", method = RequestMethod.GET)
    public String listCatalogue(Model model) {
        model.addAttribute("testcases", testCaseService.listAllTestCases());
        model.addAttribute("totals", testCaseService.getTotals());

        return "catalogue";
    }

    @RequestMapping("portal/testcase/{id}")
    public String showTestCase(@PathVariable Integer id, Model model) {
        model.addAttribute("testcase", testCaseService.getTestCaseById(id));
        return "testcase";
    }

    @RequestMapping(value = "/portal/testcases/addcase", method = RequestMethod.GET)
    public String displayAddCase(ModelMap model, TestCase testCase) {
        return "add-case";
    }

    @RequestMapping(value = "/portal/testcases/addcase", method = RequestMethod.POST)
    public String addCase(Model model, @RequestParam Integer id, @RequestParam String name, @RequestParam String description,
                          @RequestParam double timeSaving, @RequestParam Integer toolId) {
        tcsi.addTestCase(id, name, description, timeSaving, toolId);
        return "redirect:/portal/testcases";
    }

    @RequestMapping(value = "/portal/testcases/updatecase", method = RequestMethod.GET)
    public String didsplayUpdateCase(ModelMap model, @RequestParam Integer id) {
        TestCase testCase = testCaseRepository.findOne(Long.valueOf(id));
        model.put("testCase", testCase);
        return "add-case";
    }


    @RequestMapping(value = "/portal/testcases/updatecase", method = RequestMethod.POST)
    public String updateCase(Model model, @RequestParam Integer id, @RequestParam String name, @RequestParam String description,
                             @RequestParam double timeSaving, @RequestParam Integer toolId) {
        TestCase testCase = new TestCase();
        TestTool testTool = testToolRepository.findOne(Long.valueOf(toolId));
        testCase.setId(id);
        testCase.setName(name);
        testCase.setDescription(description);
        testCase.setTimeSaving(timeSaving);
        testCase.setTestTool(testTool);
        testCaseRepository.save(testCase);
        return "redirect:/portal/testcases";
    }
}
