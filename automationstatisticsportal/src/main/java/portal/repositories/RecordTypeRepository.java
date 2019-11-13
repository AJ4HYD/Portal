package portal.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import portal.domain.RecordType;

@RepositoryRestResource(collectionResourceRel = "recordtype", path = "recordtype")
public interface RecordTypeRepository extends PagingAndSortingRepository<RecordType, Long> {

	RecordType findByid(@Param("id") Long id);

}
