package portal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import portal.domain.TestCase;
import portal.domain.TestTool;
import portal.repositories.TestCaseRepository;
import portal.repositories.TestToolRepository;

@Service
public class TestCaseServiceImpl implements TestCaseService {

    private TestCaseRepository testCaseRepository;
	private TestToolRepository testToolRepository;

    private TestCase testCase = new TestCase();
    
    @Autowired
    public void setTestCaseRepository(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
    }

	@Autowired
	public void setTestToolRepository(TestToolRepository testToolRepository) {
		this.testToolRepository = testToolRepository;
	}
    
    @Override
    public Iterable<TestCase> listAllTestCases() {
        return testCaseRepository.findAll();
    }

    @Override
    public TestCase getTestCaseById(Integer id) {
        return testCaseRepository.findOne((long)id);
    }

	@Override
	public ResponseEntity<?> add(TestCase input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TestCase getTotals() {
		Iterable<TestCase> tc = testCaseRepository.findAll();
		TestCase totals = new TestCase();
		for (TestCase t: tc){
			totals.incrementGeneratedDataCountBy(t.getGeneratedDataCount());
			totals.incrementTotalRuntimeBy(t.getTotalRuntime());
			totals.increaseTotalPassBy(t.getTotalPass());
			totals.increaseTotalFailBy(t.getTotalFail());
			totals.incrementTimeSaving(t.getTimeSaving());
		}
		return totals;
	}

	public void addTestCase(long id, String name, String description, double timeSaving, long toolId) {
		testCase.setId(id);
		testCase.setName(name);
		testCase.setDescription(description);
		testCase.setTimeSaving(timeSaving);
        TestTool testTool = testToolRepository.findOne(toolId);
        testCase.setTestTool(testTool);
        testCaseRepository.save(testCase);
	}
}
