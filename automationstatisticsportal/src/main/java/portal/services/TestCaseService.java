package portal.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import portal.domain.TestCase;

public interface TestCaseService {
	
	Iterable<TestCase> listAllTestCases();
	TestCase getTestCaseById(Integer id);
	ResponseEntity<?> add(@RequestBody TestCase input);
	TestCase getTotals();
}
