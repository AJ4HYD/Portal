package portal.services;

import java.net.URI;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import portal.domain.*;
import portal.repositories.*;

@Service
public class TestRunServiceImpl implements TestRunService {

    private TestRunRepository testRunRepository;
    private TestSuiteRepository testSuiteRepository;
    private TestCaseRepository testCaseRepository;
    private TestToolRepository testToolRepository;
    private RecordTypeRepository recordTypeRepository;
    private PhaseRepository phaseRepository;

    // repositories
    @Autowired
    public void setTestRunRepository(TestRunRepository testRunRepository) {
        this.testRunRepository = testRunRepository;
    }

    @Autowired
    public void setTestSuiteRepository(TestSuiteRepository testSuiteRepository) {
        this.testSuiteRepository = testSuiteRepository;
    }

    @Autowired
    public void setTestCaseRepository(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
    }

    @Autowired
    public void setTesttOOLRepository(TestToolRepository testToolRepository) {
        this.testToolRepository = testToolRepository;
    }

    @Autowired
    public void setRecordTypeRepository(RecordTypeRepository recordTypeRepository) {
        this.recordTypeRepository = recordTypeRepository;
    }

    @Autowired
    public void setPhaseRepository(PhaseRepository phaseRepository) {
        this.phaseRepository = phaseRepository;
    }

    // get methods
    @Override
    public List<TestRun> getAllTestRuns() {
        return (List<TestRun>) testRunRepository.findAllByOrderByIdDesc();
    }

    @Override
    public List<TestRun> getTestRunsInRange(Date start, Date end) {
        return (List<TestRun>) testRunRepository.findByDateRange(start, end);
    }

    @Override
    public TestRun getTestRunById(Integer id) {
        return testRunRepository.findOne((long) id);
    }

    // logic for add test run
    @Override
    public ResponseEntity<?> add(@RequestBody TestRun input) {
        //validate the submission according to API rules
        if (!this.valid(input)) {
            return ResponseEntity.badRequest().build();
        }

        // save the test run
        TestRun result = testRunRepository.save(input);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();

        processTestRunData(result);

        return ResponseEntity.created(location).build();
    }

    //OLU CREATING A SERVICE
    @Override
    public ResponseEntity<?> addRun(TestRun input) {
        //validate the submission according to API rules
        if (!this.validate(input)) {
            return ResponseEntity.badRequest().build();
        }

        TestRun result = testRunRepository.save(input);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();

        processWipTestRunData(result);

        return ResponseEntity.created(location).build();
    }

    private void processTestRunData(TestRun result) {
        System.out.println("Process called for " + result.getTestSuite().getSuiteId());
        // update runtime total for test TOOL
        TestSuite suite = testSuiteRepository.findBySuiteId(result.getTestSuite().getSuiteId());
        TestTool tool = testToolRepository.findById(suite.getTestTool().getId());
        TestCase testCase = testCaseRepository.findById(suite.getTestCase().getId());

        // update runtime total against the test CASE and tool
        testCase.incrementTotalRuntimeBy(result.getRuntimeMilliseconds());
        testCase.incrementRunCount();
        tool.incrementTotalRuntimeBy(result.getRuntimeMilliseconds());
        tool.incrementRunCount();


        // update the pass/fail count gainst the test case
        if (result.getResult() == null) {
            testCase.incrementTotalWarnings();
        } else if (result.getResult() == true) {
            // update the time saving for the tool and test case
            if (testCase.getTimeSavingMultiplier() > 0) {
                tool.incrementTimeSaving((Double) (1 * testCase.getTimeSavingMultiplier()));
                testCase.incrementTimeSaving((Double) (1 * testCase.getTimeSavingMultiplier()));
            } else {
                tool.incrementTimeSaving((Double) (1 * tool.getTimeSavingMultiplier()));
                testCase.incrementTimeSaving((Double) (1 * tool.getTimeSavingMultiplier()));
            }

            // if the result was pass and data was generated, update total data
            if (result.getGeneratedDataCount() != null && result.getGeneratedDataType() != null) {
                testCase.incrementGeneratedDataCountBy(result.getGeneratedDataCount());
                tool.incrementGeneratedDataCountBy(result.getGeneratedDataCount());
                RecordType r = recordTypeRepository.findOne(result.getGeneratedDataType().getId());
                r.incrementGeneratedDataCountBy(result.getGeneratedDataCount());
                recordTypeRepository.save(r);
            }
            testCase.incrementTotalPass();
        } else if (result.getResult() == false) {
            testCase.incrementTotalFail();
        }

        // save updated test case information
        testCaseRepository.save(testCase);
        // save updated test tool information
        testToolRepository.save(tool);

    }

    //Olus VERSION OF PROCESSING DATA BY TEST CASE AND NOT TEST SUITE
    private void processWipTestRunData(TestRun result) {
        System.out.println("Process called for " + result.getTestCase().getId());
        // update runtime total for test TOOL
        TestCase tcase = testCaseRepository.findById(result.getTestCase().getId());
        TestTool tool = testToolRepository.findById(tcase.getTestTool().getId());

        // update runtime total against the test CASE and tool
        tcase.incrementTotalRuntimeBy(result.getRuntimeMilliseconds());
        tcase.incrementRunCount();
        tool.incrementTotalRuntimeBy(result.getRuntimeMilliseconds());
        tool.incrementRunCount();


        // update the pass/fail count gainst the test case
        if (result.getResult() == null) {
            tcase.incrementTotalWarnings();
        } else if (result.getResult() == true) {
            // update the time saving for the tool and test case
            if (tcase.getTimeSavingMultiplier() > 0) {
                tool.incrementTimeSaving((Double) (1 * tcase.getTimeSavingMultiplier()));
                tcase.incrementTimeSaving((Double) (1 * tcase.getTimeSavingMultiplier()));
            } else {
                tool.incrementTimeSaving((Double) (1 * tool.getTimeSavingMultiplier()));
                tcase.incrementTimeSaving((Double) (1 * tool.getTimeSavingMultiplier()));
            }

            // if the result was pass and data was generated, update total data
            if (result.getGeneratedDataCount() != null && result.getGeneratedDataType() != null && result.getPhaseType() != null) {
                tcase.incrementGeneratedDataCountBy(result.getGeneratedDataCount());
                tool.incrementGeneratedDataCountBy(result.getGeneratedDataCount());
                RecordType r = recordTypeRepository.findOne(result.getGeneratedDataType().getId());
                r.incrementGeneratedDataCountBy(result.getGeneratedDataCount());
                recordTypeRepository.save(r);
                Phase p = phaseRepository.findOne(result.getPhaseType().getId());
                p.incrementPhaseDataGeneratedCountBy(result.getGeneratedDataCount());
                phaseRepository.save(p);
            }
            tcase.incrementTotalPass();
        } else if (result.getResult() == false) {
            tcase.incrementTotalFail();
        }

        // save updated test case information
        testCaseRepository.save(tcase);
        // save updated test tool information
        testToolRepository.save(tool);

    }


    private boolean valid(TestRun input) {
        // user must not provide an id (cant overwrite)
        if (input.getId() != null) {
            System.out.println("user must not provide an id (cant overwrite)");
            return false;
        }

        // must have start and end time
        if (input.getStartDateTime() == null || input.getEndDateTime() == null) {
            System.out.println("must have start and end time");
            return false;
        }
        // must have valid test suite id
        if (input.getTestSuite() == null) {
            System.out.println("must have valid test suite id");
            return false;
        }

        if (testSuiteRepository.findBySuiteId(input.getTestSuite().getSuiteId()) == null) {
            System.out.println("must have valid test suite");
            return false;
        }

        // test suite must be linked to a tool
        if (testToolRepository.findById(testSuiteRepository.findBySuiteId(input.getTestSuite().getSuiteId()).getTestTool().getId()) == null) {
            System.out.println("must have valid test tool");
            return false;
        }


        // must have data count and type
        if (input.getGeneratedDataCount() != null && input.getGeneratedDataType() == null) {
            System.out.println("must have data count and type ");
            return false;
        }

        if (input.getGeneratedDataCount() == null && input.getGeneratedDataType() != null) {
            System.out.println("must have data count and type ");
            return false;
        }

        return true;
    }

    //VALIDATION by test case
    private boolean validate(TestRun input) {

        // user must not provide an id (cant overwrite)
        if (input.getId() != null) {
            System.out.println("user must not provide an id (cant overwrite)");
            return false;
        }

        // must have start and end time
        if (input.getStartDateTime() == null || input.getEndDateTime() == null) {
            System.out.println("must have start and end time");
            return false;
        }

        // must have valid test case id
        if (input.getTestCase() == null) {
            System.out.println("must have valid test case id");
            return false;
        }

        if (testCaseRepository.findById(input.getTestCase().getId()) == null) {
            System.out.println("must have valid test case");
            return false;
        }

        // olus test case must be linked to a tool
        if (testToolRepository.findById(testCaseRepository.findById(input.getTestCase().getId()).getTestTool().getId()) == null) {
            System.out.println("must have valid test tool");
            return false;
        }

        // must have data count and type
        if (input.getGeneratedDataCount() != null && input.getGeneratedDataType() == null) {
            System.out.println("must have data count and type ");
            return false;
        }

        if (input.getGeneratedDataCount() == null && input.getGeneratedDataType() != null) {
            System.out.println("must have data count and type ");
            return false;
        }

        return true;
    }


}
