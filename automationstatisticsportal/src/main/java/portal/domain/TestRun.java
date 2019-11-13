package portal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
public class TestRun implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true) // null result is warning
    private Boolean result;
    private Date startDateTime;
    private Date endDateTime;
    private Long runtimeMilliseconds;
    @Column(nullable = true)
    private String message;
    @Column(nullable = true)
    private String username;
    @Column(nullable = true)
    private Long GeneratedDataCount;

    @OneToOne
    @JoinColumn(name = "record_type_id", nullable = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private RecordType GeneratedDataType;
    //ADDING TEST PHASES RECORDS
    @OneToOne
    @JoinColumn(name = "phase_id", nullable = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Phase PhaseType;

    @ManyToOne
    @JoinColumn(name = "test_suite_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private TestSuite TestSuite;
    //TRYING TO REPLACE TEST SUITE FOR TEST CASE
    @ManyToOne
    @JoinColumn(name = "test_case_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private TestCase TestCase;

    @PrePersist
    public void calculateDuration() {
        this.runtimeMilliseconds = endDateTime.getTime() - startDateTime.getTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getUsername() {
        return username;
    }

    public RecordType getGeneratedDataType() {
        return GeneratedDataType;
    }

    public void setGeneratedDataType(RecordType generatedDataType) {
        GeneratedDataType = generatedDataType;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Long getGeneratedDataCount() {
        return GeneratedDataCount;
    }

    public void setGeneratedDataCount(Long generatedDataCount) {
        GeneratedDataCount = generatedDataCount;
    }

    public Long getRuntimeMilliseconds() {
        return runtimeMilliseconds;
    }

    public void setRuntimeMilliseconds(Long runtimeMilliseconds) {
        this.runtimeMilliseconds = runtimeMilliseconds;
    }

    public TestSuite getTestSuite() {
        return TestSuite;
    }

    public void setTestSuite(TestSuite testSuite) {
        this.TestSuite = testSuite;
    }

    public TestCase getTestCase() {
        return TestCase;
    }

    public void setTestCase(TestCase testCase) {
        this.TestCase = testCase;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Phase getPhaseType() {
        return PhaseType;
    }

    public void setPhaseType(Phase phaseType) {
        PhaseType = phaseType;
    }
}
