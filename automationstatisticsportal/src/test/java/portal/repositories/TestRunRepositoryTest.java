package portal.repositories;

import portal.configuration.RepositoryConfiguration;
import portal.domain.TestCase;
import portal.domain.TestRun;
import portal.domain.TestSuite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {RepositoryConfiguration.class})
public class TestRunRepositoryTest {

    private TestRunRepository testRunRepository;
    private TestCaseRepository testCaseRepository;
    private TestSuiteRepository testSuiteRepository;
    
    @Autowired
    public void setTestRunRepository(TestRunRepository testRunRepository) {
        this.testRunRepository = testRunRepository;
    }
    @Autowired
    public void setTestCaseRepository(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
    }
    @Autowired
    public void setTestSuiteRepository(TestSuiteRepository testSuiteRepository) {
        this.testSuiteRepository = testSuiteRepository;
    }
    
    @Test
    public void testSaveTestRun(){
    	
    	// number of test cases in database
    	long originalCount = testRunRepository.count();
    	
        // create the test case
        TestCase testCase = new TestCase();
        testCase.setId(1);
        testCase.setName("Agent Charities");
		testCaseRepository.save(testCase);

		//create test suite
		TestSuite testSuite = new TestSuite();
		testSuite.setSuiteId(1);
		testSuite.setTestCase(testCase);
		testSuiteRepository.save(testSuite);
		
        // create test run
        TestRun testRun = new TestRun();
        testRun.setResult(true);
        testRun.setUsername(System.getProperty("user.name"));
        testRun.setTestSuite(testSuite);
        testRun.setStartDateTime(new Date());
        testRun.setEndDateTime(new Date());

        //save testRun, verify has ID value after save
        assertNull(testRun.getId()); //null before save
        testRunRepository.save(testRun);
        assertNotNull(testRun.getId()); //not null after save
        //fetch from DB
        TestRun fetchedTestRun  = testRunRepository.findOne(testRun.getId());

        //should not be null
        assertNotNull(fetchedTestRun);

        //should equal
        SimpleDateFormat date = new SimpleDateFormat("dd-MMM-yyyy");
        assertEquals(testRun.getId(), fetchedTestRun.getId());
        assertEquals(date.format(testRun.getStartDateTime()), date.format(fetchedTestRun.getStartDateTime()));

        //verify count of testRun in DB
        long count = testRunRepository.count();
        assertEquals(count, originalCount+1);

        //delete the testRun
        testRunRepository.delete(testRun);
        count = testRunRepository.count();
        assertEquals(count, originalCount);
    }
}