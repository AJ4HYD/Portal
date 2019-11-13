package portal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.domain.RecordType;
import portal.domain.TestCase;
import portal.domain.TestTool;
import portal.repositories.RecordTypeRepository;
import portal.repositories.TestCaseRepository;
import portal.repositories.TestToolRepository;

@Service
public class AdminServiceImpl implements AdminService {

    private TestToolRepository testToolRepository;
    private TestCaseRepository testCaseRepository;
    private RecordTypeRepository recordTypeRepository;
    
    @Autowired
    public void setTestToolRepository(TestToolRepository testToolRepository) {
        this.testToolRepository = testToolRepository;
    }
    @Autowired
    public void setTestCaseRepository(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
    }
    @Autowired
    public void setRecordTypeRepository(RecordTypeRepository recordTypeRepository) {
        this.recordTypeRepository = recordTypeRepository;
    }
    
	@Override
	public void resetStatisticsToZero() {
		for (TestTool t : testToolRepository.findAll()){
				t.setTotalRuntime((long)0);
				t.setGeneratedDataCount((long)0);
				t.setTimeSaving((Double)0.0);
				t.setRunCount((long)0);
				testToolRepository.save(t);
		}
		for (TestCase t : testCaseRepository.findAll()){
				t.setTotalRuntime((long)0);
				t.setGeneratedDataCount((long)0);
				t.setTotalPass((long)0);
				t.setTotalFail((long)0);
				t.setTotalWarnings((long)0);
				testCaseRepository.save(t);
		}
		for (RecordType r : recordTypeRepository.findAll()){
			r.setGeneratedDataCount((long)0);
			recordTypeRepository.save(r);
		}
	}
}
