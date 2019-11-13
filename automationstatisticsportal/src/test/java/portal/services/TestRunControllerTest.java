package portal.services;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import portal.domain.TestCase;
import portal.domain.TestRun;
import portal.domain.TestSuite;
import portal.domain.TestTool;
import portal.repositories.RecordTypeRepository;
import portal.repositories.TestCaseRepository;
import portal.repositories.TestRunRepository;
import portal.repositories.TestSuiteRepository;
import portal.repositories.TestToolRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestRunControllerTest {

	@Autowired
    private TestRunServiceImpl testRunService;
	@Autowired
    private TestRunRepository testRunRepository;
	@Autowired
    private TestCaseRepository testCaseRepository;
	@Autowired
    private TestToolRepository testToolRepository;
	@Autowired
    private TestSuiteRepository testSuiteRepository;
	@Autowired
    private RecordTypeRepository recordTypeRepository;
	
	TestRun testRun = new TestRun();
	TestSuite testSuite = new TestSuite();
	TestTool testTool = new TestTool();
	TestCase testCase = new TestCase();
	
	@Before 
	public void createTestData(){

        // create the test case
        testCase.setId(1);
        testCase.setName("Agent Charities");
		testCaseRepository.save(testCase);

		// create test tool
		testTool.setId(1);
		testToolRepository.save(testTool);

		//create test suite
		testSuite.setSuiteId(1);
		testSuite.setTestCase(testCase);
		testSuite.setTestTool(testTool);
		testSuiteRepository.save(testSuite);
		
        // create a test run object
        testRun.setTestSuite(testSuite);
        testRun.setStartDateTime(new Date());
        testRun.setEndDateTime(new Date());
        testRun.setGeneratedDataType(recordTypeRepository.findByid((long)1));
	}
	
    @Test
    public void aggregateDataCountWhenResultIsPass(){

        // test run result is true with 5 data items
        testRun.setResult(true);
        testRun.setGeneratedDataCount((long)5);
        testRunService.add(testRun);
        testRun.setId(null);
        // test run result is false with 3 data items
        testRun.setResult(false);
        testRun.setGeneratedDataCount((long)3);
        testRunService.add(testRun);
        testRun.setId(null);
        System.out.println(testRunRepository.count()); //output = 0
        
        // test tool and test case repositories should show count of 5
        assertEquals((long)5, testCaseRepository.findById(testSuite.getTestCase().getId()).getGeneratedDataCount().longValue());
        assertEquals((long)5, testToolRepository.findById(testSuite.getTestTool().getId()).getGeneratedDataCount().longValue());
        // with one pass and one fail
        assertEquals((long)1, testCaseRepository.findById(testSuite.getTestCase().getId()).getTotalPass().longValue());
        assertEquals((long)1, testCaseRepository.findById(testSuite.getTestCase().getId()).getTotalFail().longValue());
        
        // add another 3 succesul data items
        testRun.setResult(true);
        testRun.setGeneratedDataCount((long)3);
        testRunService.add(testRun);
        
        // test tool and test case repositories should show count of 8
        assertEquals((long)8, testCaseRepository.findById(testSuite.getTestCase().getId()).getGeneratedDataCount().longValue());
        assertEquals((long)8, testToolRepository.findById(testSuite.getTestTool().getId()).getGeneratedDataCount().longValue());
    }
    
    @Test
    public void recordTotalPasses(){

        // create one pass amd a fail
        testRun.setResult(false);
        testRun.setGeneratedDataCount(null);
        testRun.setGeneratedDataType(null);
        testRunService.add(testRun);
        testRun.setId(null);
        testRun.setResult(true);
        testRunService.add(testRun);
        testRun.setId(null);
        
        // now we should have one pass
        assertEquals((long)1, testCaseRepository.findById(testSuite.getTestCase().getId()).getTotalPass().longValue());
        
        // recording another should make it two
        testRunService.add(testRun);
        assertEquals((long)2, testCaseRepository.findById(testSuite.getTestCase().getId()).getTotalPass().longValue());
    }
    
    @Test
    public void recordTotalFailures(){

        // create one pass amd a fail
        testRun.setResult(true);
        testRun.setGeneratedDataCount(null);
        testRun.setGeneratedDataType(null);
        testRunService.add(testRun);
        testRun.setId(null);
        testRun.setResult(false);
        testRunService.add(testRun);
        testRun.setId(null);
        
        // now we should have one fail
        assertEquals((long)1, testCaseRepository.findById(testSuite.getTestCase().getId()).getTotalFail().longValue());
        
        // recording another should make it two
        testRunService.add(testRun);
        assertEquals((long)2, testCaseRepository.findById(testSuite.getTestCase().getId()).getTotalFail().longValue());
    }
}
