package portal.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TestSuite {
	@Id
    private Long suiteId;
	@ManyToOne
    private TestTool testTool;
	@ManyToOne
    private TestCase testCase;
	
	public Long getSuiteId() {
		return suiteId;
	}
	public void setSuiteId(long parseInt) {
		this.suiteId = (long) parseInt;
	}

//	public void setCaseId(Long suiteId) {
//		this.suiteId = suiteId;
//	}
	public TestTool getTestTool() {
		return testTool;
	}
	public void setTestTool(TestTool testTool) {
		if (testTool.getId() == null){
			System.out.println("invalid object" + testTool);
		}
		else{
			this.testTool = testTool;
		}

	}
	public TestCase getTestCase() {
		return testCase;
	}
	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestSuite other = (TestSuite) obj;
		if (testTool == null) {
			if (other.testTool != null)
				return false;
		} else if (!testTool.equals(other.testTool))
			return false;
		if (suiteId == null) {
			if (other.suiteId != null)
				return false;
		} else if (!suiteId.equals(other.suiteId))
			return false;
		if (testCase == null) {
			if (other.testCase != null)
				return false;
		} else if (!testCase.equals(other.testCase))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((suiteId == null) ? 0 : suiteId.hashCode());
		result = prime * result + ((testCase == null) ? 0 : testCase.hashCode());
		result = prime * result + ((testTool == null) ? 0 : testTool.hashCode());
		return result;
	}



}
