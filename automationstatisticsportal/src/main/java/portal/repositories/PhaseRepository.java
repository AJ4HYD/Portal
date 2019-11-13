package portal.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import portal.domain.Phase;
import portal.domain.RecordType;

@RepositoryRestResource(collectionResourceRel = "phase", path = "phase")
public interface PhaseRepository extends PagingAndSortingRepository<Phase, Long> {

	Phase findByid(@Param("id") Long id);

}
