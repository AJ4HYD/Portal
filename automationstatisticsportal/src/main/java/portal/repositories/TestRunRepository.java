package portal.repositories;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.TemporalType;

import portal.domain.TestRun;

public interface TestRunRepository extends PagingAndSortingRepository<TestRun, Long> {

	TestRun findByid(@Param("id") Long id);
	List<TestRun> findAllByOrderByIdDesc();

   
	@Query("SELECT o FROM TestRun o WHERE o.startDateTime BETWEEN :from AND :to")
	Collection<TestRun> findByDateRange(
	    @Param("from") @Temporal(TemporalType.TIMESTAMP) Date startDay,
	    @Param("to") @Temporal(TemporalType.TIMESTAMP) Date endDay);

	@Query("SELECT o FROM TestRun o WHERE o.startDateTime BETWEEN :from AND :to")
	Page<TestRun> findByDateRange(
		Pageable pageable,
	    @Param("from") @Temporal(TemporalType.TIMESTAMP) Date startDay,
	    @Param("to") @Temporal(TemporalType.TIMESTAMP) Date endDay);
	
	@Query("select count(*) from TestRun o where o.TestSuite in (select id from TestSuite s where s.testTool.id = :tool)")
	Long findTestRunsForTool(@Param("tool") Long tool);
};