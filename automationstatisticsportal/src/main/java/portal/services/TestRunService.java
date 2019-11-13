package portal.services;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import portal.domain.TestRun;

public interface TestRunService {	
	List<TestRun> getAllTestRuns();
	List<TestRun> getTestRunsInRange(Date start, Date end);
	TestRun getTestRunById(Integer id);
	ResponseEntity<?> add(@RequestBody TestRun input);
	ResponseEntity<?> addRun(@RequestBody TestRun input);

}
