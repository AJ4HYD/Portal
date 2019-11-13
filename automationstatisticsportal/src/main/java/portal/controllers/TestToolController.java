package portal.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import portal.repositories.TestToolRepository;
import portal.services.TestToolService;
import portal.services.TestToolServiceImpl;

@Controller
public class TestToolController {
    private TestToolService testToolService;
    private TestToolRepository testToolRepository;
    @Autowired
    private TestToolServiceImpl ttsi;
    @Autowired
    public void setTestToolRepository(TestToolRepository testToolRepository) {
        this.testToolRepository = testToolRepository;
    }
    @Autowired
    public void setTestRunService(TestToolService testToolService) {
        this.testToolService = testToolService;
    }

    @RequestMapping(value = "portal/testtools", method = RequestMethod.GET)
    public String listTestTools(Model model) {
        model.addAttribute("testtools", testToolService.listAllTestTools());
        model.addAttribute("totals", testToolService.getTotals());
        return "testtools";
    }

    @RequestMapping(value = "portal/testtools/between/{start}/and/{end}", method = RequestMethod.GET)
    public String testRunsForDateRangeRedirect(HttpServletRequest request, Model model,
                                               @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                               @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {
        Iterable<TestTool> tt = testToolService.listAllTestToolsByDate(start, end);
        model.addAttribute("testtools", tt);
        model.addAttribute("totals", testToolService.getTotals(tt));
        return "testtools";
    }

    @RequestMapping("portal/testtool/{id}")
    public String showTestTool(@PathVariable Integer id, Model model) {
        model.addAttribute("testtool", testToolService.getTestToolById(id));
        return "testtool";
    }

    @RequestMapping(value = "/portal/testtool/addtool", method = RequestMethod.GET)
    public String displayAddTool(ModelMap model, TestTool testTool) {
        model.addAttribute("testTool", testTool);
        return "add-tool";
    }

    @RequestMapping(value = "/portal/testtool/addtool", method = RequestMethod.POST)
    public String addTool(ModelMap model, @Valid TestTool testTool, BindingResult result) {
        if(result.hasErrors()){
            return "add-tool";
        }
        ttsi.addTestTool(testTool.getId(), testTool.getName(), testTool.getDescription(), testTool.getToolClass(), testTool.getTimeSaving());
        return "redirect:/portal/testtools";
    }

    @RequestMapping(value = "/portal/testtool/updatetool", method = RequestMethod.GET)
    public String displayUpdateTool(ModelMap model, @RequestParam Integer id){
        TestTool testTool = testToolRepository.findOne(Long.valueOf(id));
        model.put("testTool", testTool);
        return "add-tool";
    }

    @RequestMapping(value = "/portal/testtool/updatetool", method = RequestMethod.POST)
    public String updateTool(Model model, @RequestParam Integer id, @RequestParam String name, @RequestParam String description,
                             @RequestParam String toolClass, @RequestParam double timeSaving){
        TestTool testTool = new TestTool();
        testTool.setId(id);
        testTool.setName(name);
        testTool.setDescription(description);
        testTool.setToolClass(toolClass);
        testTool.setTimeSaving(timeSaving);
        testToolRepository.save(testTool);
        return "redirect:/portal/testtools";
    }
}
