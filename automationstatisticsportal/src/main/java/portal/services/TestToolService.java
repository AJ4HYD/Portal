package portal.services;

import java.util.Date;

import portal.domain.TestTool;

public interface TestToolService {
	Iterable<TestTool> listAllTestTools();
	Iterable<TestTool> listAllTestToolsByDate(Date start, Date end);
	TestTool getTestToolById(Integer id);
	TestTool getTotals();
	TestTool getTotals(Iterable<TestTool> tt);
}
