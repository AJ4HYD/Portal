package portal.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import portal.domain.TestCase;
import portal.domain.TestTool;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "testcases", path = "testcases")
public interface TestCaseRepository extends PagingAndSortingRepository<TestCase, Long> {

	TestCase findByName(@Param("name") String name);
	TestCase findById(@Param("id") long id);
	@Query("select count(*) from TestSuite o where o.testTool = :tool)")
	Long findTestCasesForTool(@Param("tool") TestTool tool);
	//List<TestCase> findAllOrderByTestToolAsc(TestTool testTool);
}
