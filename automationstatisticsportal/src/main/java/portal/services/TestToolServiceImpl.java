package portal.services;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.domain.TestCase;
import portal.domain.TestRun;
import portal.domain.TestSuite;
import portal.domain.TestTool;
import portal.repositories.TestCaseRepository;
import portal.repositories.TestRunRepository;
import portal.repositories.TestSuiteRepository;
import portal.repositories.TestToolRepository;

@Service
public class TestToolServiceImpl implements TestToolService {

    private TestToolRepository testToolRepository;
    private TestRunRepository testRunRepository;
    private TestSuiteRepository testSuiteRepository;
    private TestCaseRepository testCaseRepository;
    private TestTool testTool = new TestTool();
    private TestCase testCase = new TestCase();
    private TestSuite testSuite = new TestSuite();

    @Autowired
    public void setTestToolRepository(TestToolRepository testToolRepository) {
        this.testToolRepository = testToolRepository;
    }

    @Autowired
    public void setTestCaseRepository(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
    }

    @Autowired
    public void setTestRunRepository(TestRunRepository testRunRepository) {
        this.testRunRepository = testRunRepository;
    }

    @Override
    public Iterable<TestTool> listAllTestTools() {
        Iterable<TestTool> allTesttools = testToolRepository.findAll();

        Date endDate = new Date();
        Date startDate = org.apache.commons.lang3.time.DateUtils.addYears(endDate, -1);

        List<TestTool> testToolbyDate = listAllTestToolsByDate(startDate, endDate);
        Set<TestTool> testtoolvalues = testToolbyDate.stream().collect(Collectors.toSet());

        Iterator<TestTool> it = allTesttools.iterator();

        while (it.hasNext()) {
            testtoolvalues.add(it.next());
        }

        return testtoolvalues.stream().sorted(Comparator.comparing(TestTool::getId)).collect(Collectors.toList());

    }

    @Autowired
    public void setTestTooLRepository(TestSuiteRepository testSuiteRepository) {
        this.testSuiteRepository = testSuiteRepository;
    }

    @Override
    public List<TestTool> listAllTestToolsByDate(Date start, Date end) {
        Map<Long, TestTool> toolContainer = new HashMap<Long, TestTool>();

        for (TestRun r : testRunRepository.findByDateRange(start, end)) {
            // categorisation logic
//            TestSuite suite = testSuiteRepository.findBySuiteId(r.getTestSuite().getSuiteId());
            TestCase testCase = testCaseRepository.findById(r.getTestCase().getId());
            TestTool tool = testToolRepository.findById(testCase.getTestTool().getId());

            if (toolContainer.get(tool.getId()) == null) {
                toolContainer.put(tool.getId(), tool);
                toolContainer.get(tool.getId()).setTotalRuntime((long) 0);
                toolContainer.get(tool.getId()).setTimeSaving(0.0);
                toolContainer.get(tool.getId()).setGeneratedDataCount((long) 0);
                toolContainer.get(tool.getId()).setRunCount((long) 1);
            }
            if (r.getRuntimeMilliseconds() > 0) {
                toolContainer.get(tool.getId()).incrementTotalRuntimeBy(r.getRuntimeMilliseconds());
            }
            // only increment time saving for pass results
            if (r.getResult()) {
                toolContainer.get(tool.getId()).incrementTimeSaving((Double) (1 * tool.getTimeSavingMultiplier()));
            }
            if (r.getGeneratedDataCount() != null) {
                toolContainer.get(tool.getId()).incrementGeneratedDataCountBy(r.getGeneratedDataCount());
            }

            toolContainer.get(tool.getId()).incrementRunCount();

        }
        List<TestTool> output = new ArrayList<TestTool>();
        output.addAll(toolContainer.values());
        return output;
    }

    @Override
    public TestTool getTestToolById(Integer id) {
        return testToolRepository.findOne((long) id);
    }

    @Override
    public TestTool getTotals() {
        Iterable<TestTool> tt = testToolRepository.findAll();
        TestTool totals = new TestTool();

        for (TestTool t : tt) {
            totals.incrementGeneratedDataCountBy(t.getGeneratedDataCount());
            totals.incrementTotalRuntimeBy(t.getTotalRuntime());
            totals.incrementRunCountBy(t.getRunCount());
            totals.incrementTimeSaving(t.getTimeSaving());
        }
        return totals;
    }

    @Override
    public TestTool getTotals(Iterable<TestTool> tt) {
        TestTool totals = new TestTool();

        for (TestTool t : tt) {
            totals.incrementGeneratedDataCountBy(t.getGeneratedDataCount());
            totals.incrementTotalRuntimeBy(t.getTotalRuntime());
            totals.incrementRunCountBy(t.getRunCount());
            totals.incrementTimeSaving(t.getTimeSaving());
        }
        return totals;
    }

    public void addTestTool(long id, String name, String description, String toolClass, double timeSaving) {
        testTool.setId(id);
        testTool.setName(name);
        testTool.setDescription(description);
        testTool.setToolClass(toolClass);
        testTool.setTimeSaving(timeSaving);
        testToolRepository.save(testTool);
    }

}
