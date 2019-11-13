package portal.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.domain.*;
import portal.repositories.*;

@Service
public class MetricsServiceImpl implements MetricsService {

    private TestRunRepository testRunRepository;
    private TestCaseRepository testCaseRepository;
    private TestToolRepository testToolRepository;
    private RecordTypeRepository recordTypeRepository;
    private PhaseRepository phaseRepository;

    @Autowired
    public void setTestRunRepository(TestRunRepository testRunRepository) {
        this.testRunRepository = testRunRepository;
    }

    @Autowired
    public void setTestCaseRepository(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
    }

    @Autowired
    public void setTestTooLRepository(TestToolRepository testToolRepository) {
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

    private Metrics grandTotals(Map<String, Metrics> metricsContainer) {
        Metrics totals = new Metrics();
        for (Map.Entry<String, Metrics> m : metricsContainer.entrySet()) {
            totals.incrementTotalRuntime(m.getValue().getTotalRuntime());
            totals.incrementTotalRuntimeSaved((Double) (m.getValue().getTotalRuntimeSaved()));
            totals.incrementTotalDataItemsGenerated(m.getValue().getTotalDataItemsGenerated());
            totals.incrementTotalTools(m.getValue().getTotalTools());
            totals.incrementTotalTestRuns(m.getValue().getTotalTestRuns());
            totals.incrementTotalTestCases(m.getValue().getTotalTestCases());
        }
        return totals;
    }

    @Override
    public Map<String, Metrics> getMetricsForHomePage(Date start, Date end) {
        Map<String, Metrics> metricsContainer = new HashMap<String, Metrics>();

        for (TestRun r : testRunRepository.findByDateRange(start, end)) {
            // categorisation logic

            if (r.getTestCase() != null){
                TestCase testCase = testCaseRepository.findById(r.getTestCase().getId());
                TestTool tool = testToolRepository.findById(testCase.getTestTool().getId());

                if(metricsContainer.get(tool.getToolClass()) == null){
                    metricsContainer.put(tool.getToolClass(), new Metrics());
                }
                if (r.getRuntimeMilliseconds() > 0){
                    metricsContainer.get(tool.getToolClass()).incrementTotalRuntime(r.getRuntimeMilliseconds());
                }
                // only increment time saving for pass results
                if (r.getResult()){
                    metricsContainer.get(tool.getToolClass()).incrementTotalRuntimeSaved( (Double) (1 * tool.getTimeSavingMultiplier()));
                }
                if (r.getGeneratedDataCount() != null){
                    metricsContainer.get(tool.getToolClass()).incrementTotalDataItemsGenerated(r.getGeneratedDataCount());
                }

                metricsContainer.get(tool.getToolClass()).incrementTotalTestRuns((long)1);

                if (metricsContainer.get(tool.getToolClass()).getTotalTools()==null)
                    metricsContainer.get(tool.getToolClass()).setTotalTools((long)testToolRepository.findBytoolClass(tool.getToolClass()).size());
                if (metricsContainer.get(tool.getToolClass()).getTotalTestCases()==null)
                    metricsContainer.get(tool.getToolClass()).setTotalTestCases(testCaseRepository.findTestCasesForTool(tool));
            }
        }
        metricsContainer.put("Grand Totals", grandTotals(metricsContainer));
        return metricsContainer;
    }


    @Override
    public Map<String, Metrics> getMetricsForHomePage() {
        Date endDate = new Date();
        Date startDate = org.apache.commons.lang3.time.DateUtils.addYears(endDate, -1);
        //return this.getMetricsForHomePage(testRunRepository.findByToolId((long)1).getStartDateTime(),new Date());
        return this.getMetricsForHomePage(startDate, endDate);
    }

    @Override
    public Object[][] getToolMetricsForHomePage(Date start, Date end) {
        Map<String, Integer> results = new HashMap<String, Integer>();

        for (TestRun r : testRunRepository.findByDateRange(start, end)) {
            //TestSuite suite = testSuiteRepository.findBySuiteId(r.getTestSuite().getSuiteId());
            TestCase testCase = testCaseRepository.findById(r.getTestCase().getId());
            TestTool tool = testToolRepository.findById(testCase.getTestTool().getId());

            if (!results.containsKey(tool.getId().intValue())) {
                results.put(tool.getName(), tool.getGeneratedDataCount().intValue());
            }


        }
        Object[][] result = new Object[results.size() + 1][2];
        int i = 0;
        result[i][0] = "Name";
        result[i][1] = "Number of Data Items";

        for (Entry<String, Integer> e : results.entrySet()) {
            i++;
            result[i][0] = e.getKey();
            result[i][1] = e.getValue();
        }

        return result;

    }

    @Override
    public Object[][] getToolMetricsForHomePage() {

        Object[][] result = new Object[(int) (testToolRepository.count() + 1)][2];
        result[0][0] = "Name";
        result[0][1] = "Number of Data Items";
        try {
            int i = 1;
            for (TestTool t : testToolRepository.findAll()) {
                result[i][0] = t.getName().substring(0, Math.min(24, t.getName().length())) + "...";
                result[i][1] = t.getGeneratedDataCount();
                i++;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return result;
    }

    @Override
    public Object[][] getPassFailMetricsForHomePage(Date start, Date end) {
        Long pass = (long) 0;
        Long fail = (long) 0;
        Long warn = (long) 0;

        for (TestRun r : testRunRepository.findByDateRange(start, end)) {

            if (r.getResult() == null)
                warn++;
            if (r.getResult() == true)
                pass++;
            if (r.getResult() == false)
                fail++;
        }

        Object[][] result = new Object[4][2];
        result[0][0] = "Result";
        result[0][1] = "Grand Total";
        result[1][0] = "Pass";
        result[1][1] = pass;
        result[2][0] = "Fail";
        result[2][1] = fail;
        result[3][0] = "Warn";
        result[3][1] = warn;
        return result;
    }

    @Override
    public Object[][] getPassFailMetricsForHomePage() {

        Long pass = (long) 0;
        Long fail = (long) 0;
        Long warn = (long) 0;

        for (TestCase t : testCaseRepository.findAll()) {
            pass += t.getTotalPass();
            fail += t.getTotalFail();
            warn += t.getTotalWarnings();
        }

        Object[][] result = new Object[4][2];
        result[0][0] = "Result";
        result[0][1] = "Grand Total";
        result[1][0] = "Pass";
        result[1][1] = pass;
        result[2][0] = "Fail";
        result[2][1] = fail;
        result[3][0] = "Warn";
        result[3][1] = warn;
        return result;
    }

    @Override
    public Object[][] getPhaseMetricsForHomePage() {
        Object[][] phases = new Object[(int) (phaseRepository.count() + 1)][2];
        phases[0][0] = "Phase";
        phases[0][1] = "Number of Data Items";

        try {
            int i = 1;
            for (Phase p : phaseRepository.findAll()) {
                phases[i][0] = p.getName();
                phases[i][1] = p.getPhaseDataGeneratedCount();
                i++;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return phases;
    }

    @Override
    public Object[][] getPhaseMetricsForHomePage(Date start, Date end) {
        Object[][] result = new Object[(int) (phaseRepository.count() + 1)][2];
        result[0][0] = "Phase Type";
        result[0][1] = "Number of Data Items";

        for (TestRun r : testRunRepository.findByDateRange(start, end)) {

            if (r.getPhaseType() != null) {
                if (result[r.getPhaseType().getId().intValue()][0] == null) {
                    result[r.getPhaseType().getId().intValue()][0] = phaseRepository.findByid(r.getPhaseType().getId()).getName();
                    result[r.getPhaseType().getId().intValue()][1] = phaseRepository.findByid(r.getPhaseType().getId()).getPhaseDataGeneratedCount();
                }
            }
        }
        return result;
    }

    @Override
    public Map<String, Phase> displayPhaseMetricsForHomePage() {
        Date endDate = new Date();
        Date startDate = org.apache.commons.lang3.time.DateUtils.addYears(endDate, -1);
        return this.displayPhaseMetricsForHomePage(startDate, endDate);
    }

    @Override
    public Map<String, Phase> displayPhaseMetricsForHomePage(Date start, Date end) {
        Map<String, Phase> phaseContainer = new HashMap<String, Phase>();

        for (TestRun r : testRunRepository.findByDateRange(start, end)) {

            if (r.getPhaseType() != null){
                Phase phase = phaseRepository.findByid(r.getPhaseType().getId());

                if (phaseContainer.get(phase.getName()) == null) {
                    phaseContainer.put(phase.getName(), new Phase());
                }
                if (r.getGeneratedDataCount() != null) {
                    phaseContainer.get(phase.getName()).incrementTotalByPhaseType(r.getGeneratedDataCount());
                }
            }
        }
        phaseContainer.put("Total", Totals(phaseContainer));
        return phaseContainer;
    }

    private Phase Totals(Map<String, Phase> metricsContainer) {
        Phase totals = new Phase();
        for (Map.Entry<String, Phase> p : metricsContainer.entrySet()) {
            totals.incrementTotalByPhaseType(p.getValue().getTotalByPhaseType());
        }
        return totals;
    }

    @Override
    public Object[][] getDataTypeMetricsForHomePage(Date start, Date end) {
        Object[][] result = new Object[(int) (recordTypeRepository.count() + 1)][2];
        result[0][0] = "Data Type";
        result[0][1] = "Number of Data Items";

        for (TestRun r : testRunRepository.findByDateRange(start, end)) {

            if (r.getGeneratedDataType() != null) {
                if (result[r.getGeneratedDataType().getId().intValue()][0] == null) {
                    result[r.getGeneratedDataType().getId().intValue()][0] = recordTypeRepository.findByid(r.getGeneratedDataType().getId()).getName();
                    result[r.getGeneratedDataType().getId().intValue()][1] = recordTypeRepository.findByid(r.getGeneratedDataType().getId()).getGeneratedDataCount();
                }
            }
        }
        return result;
    }

    @Override
    public Object[][] getDataTypeMetricsForHomePage() {

        Object[][] result = new Object[(int) (recordTypeRepository.count() + 1)][2];
        result[0][0] = "Data Type";
        result[0][1] = "Number of Data Items";
        try {
            int i = 1;
            for (RecordType r : recordTypeRepository.findAll()) {
                result[i][0] = r.getName();
                result[i][1] = r.getGeneratedDataCount();
                i++;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return result;
    }
}
