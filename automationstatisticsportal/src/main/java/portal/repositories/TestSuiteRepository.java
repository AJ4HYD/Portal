package portal.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import portal.domain.TestCase;
import portal.domain.TestSuite;
import portal.domain.TestTool;

@RepositoryRestResource(collectionResourceRel = "testsuites", path = "testsuites")
public interface TestSuiteRepository extends PagingAndSortingRepository<TestSuite, Long> {

	List<TestSuite> findByTestTool(@Param("testTool") TestTool testTool);
	TestSuite findBySuiteId(@Param("suiteId") Long suiteId);
	TestSuite findByTestToolAndTestCase(@Param("testTool") TestTool testTool, 
			@Param("testCase") TestCase testCase);
}