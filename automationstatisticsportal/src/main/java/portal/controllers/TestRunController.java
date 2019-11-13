package portal.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import portal.containers.Pager;
import portal.domain.TestRun;
import portal.services.TestRunService;

@Controller
public class TestRunController {

    private TestRunService testRunService;
    private static final String SESSION_ATTRIBUTE_TESTRUNLIST = "testRunList";
    private static final int TESTRUNLIST_LIST_PAGE_SIZE = 20;
    
    @Autowired
    public void setTestRunService(TestRunService testRunService) {
        this.testRunService = testRunService;
    }

    //  WEB PORTAL
    // redirectors implement pagination
    @RequestMapping(value = "portal/testruns", method = RequestMethod.GET)
	public String testRunsRedirect(HttpServletRequest request){
    	request.getSession().setAttribute(SESSION_ATTRIBUTE_TESTRUNLIST, null);
        return "redirect:/portal/testruns/page/1";
	} 
    @RequestMapping(value = "portal/testruns/between/{start}/and/{end}", method = RequestMethod.GET)
    public String testRunsForDateRangeRedirect(HttpServletRequest request, Model model, 
    	@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date start, 
    	@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date end){
    	request.getSession().setAttribute(SESSION_ATTRIBUTE_TESTRUNLIST, null);
        return "redirect:/portal/testruns/between/{start}/and/{end}/page/1";
    }
    
    // pagination
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "portal/testruns/page/{pageNumber}", method = RequestMethod.GET)
	public String testRunsPage(HttpServletRequest request, @PathVariable Integer pageNumber, Model model){
		PagedListHolder<TestRun> pagedListHolder = (PagedListHolder<TestRun>) request.getSession()
				.getAttribute(SESSION_ATTRIBUTE_TESTRUNLIST);

			pagedListHolder = new PagedListHolder<TestRun>(testRunService.getAllTestRuns());
			pagedListHolder.setPageSize(TESTRUNLIST_LIST_PAGE_SIZE);
			final int goToPage = pageNumber - 1;
			pagedListHolder.setPage(goToPage);

		request.getSession().setAttribute(SESSION_ATTRIBUTE_TESTRUNLIST, pagedListHolder);
		model.addAttribute("pager", currentPage(pagedListHolder));
		model.addAttribute("testruns", pagedListHolder);
		return "testruns";
	}
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "portal/testruns/between/{start}/and/{end}/page/{pageNumber}", method = RequestMethod.GET)
    public String testRunsForDateRange(HttpServletRequest request, @PathVariable Integer pageNumber, Model model,  
    		@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date start, 
    		@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date end){
    	
		PagedListHolder<TestRun> pagedListHolder = (PagedListHolder<TestRun>) request.getSession()
				.getAttribute(SESSION_ATTRIBUTE_TESTRUNLIST);

		if (pagedListHolder == null) {
			pagedListHolder = new PagedListHolder<TestRun>(testRunService.getTestRunsInRange(start, end));
			pagedListHolder.setPageSize(TESTRUNLIST_LIST_PAGE_SIZE);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pagedListHolder.getPageCount() && goToPage >= 0) {
				pagedListHolder.setPage(goToPage);
			}
		}
		request.getSession().setAttribute(SESSION_ATTRIBUTE_TESTRUNLIST, pagedListHolder);
		model.addAttribute("pager", currentPage(pagedListHolder));
		model.addAttribute("testruns", pagedListHolder);
		return "testruns";
    }
    

	private Pager currentPage(PagedListHolder<?> pagedListHolder) {
		int currentIndex = pagedListHolder.getPage() + 1;
		int beginIndex = Math.max(1, currentIndex - TESTRUNLIST_LIST_PAGE_SIZE);
		int endIndex = Math.min(beginIndex + TESTRUNLIST_LIST_PAGE_SIZE, pagedListHolder.getPageCount());
		int totalPageCount = pagedListHolder.getPageCount();
		int totalItems = pagedListHolder.getNrOfElements();
		String baseUrl = "";

		Pager pager = new Pager();
		pager.setBeginIndex(beginIndex);
		pager.setEndIndex(endIndex);
		pager.setCurrentIndex(currentIndex);
		pager.setTotalPageCount(totalPageCount);
		pager.setTotalItems(totalItems);
		pager.setBaseUrl(baseUrl);
		return pager;
	}
    
    @RequestMapping("portal/testrun/{id}")
    public String showTestRun(@PathVariable Integer id, Model model){
        model.addAttribute("testrun", testRunService.getTestRunById(id));
        return "testrun";
    }
    
    // REST ENDPOINT
    @CrossOrigin
    @RequestMapping(value = "testruns", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody TestRun testRun){
        return testRunService.add(testRun);
    }

    @CrossOrigin
    @RequestMapping(value = "wiptestruns", method = RequestMethod.POST)
    public ResponseEntity<?> addRun(@RequestBody TestRun testRun){
        return testRunService.addRun(testRun);
    }
}
