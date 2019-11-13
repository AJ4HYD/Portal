package portal.repositories;

import java.util.Collection;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import portal.domain.TestTool;

@RepositoryRestResource(collectionResourceRel = "testtools", path = "tools")
public interface TestToolRepository extends PagingAndSortingRepository<TestTool, Long> {

	TestTool findByName(@Param("name") String name);
	TestTool findById(@Param("id") long id);
	Collection<TestTool> findBytoolClass(@Param("toolClass") String toolClass);

}