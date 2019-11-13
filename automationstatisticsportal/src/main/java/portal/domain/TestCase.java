package portal.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TestCase implements java.io.Serializable {

	@Id
	private Long id;
	private String name;
	private String description;
	private Long totalRuntime;
	private Long generatedDataCount;
	private Long totalPass;
	private Long totalFail;
	private Long totalWarnings;
	private Double timeSavingMultiplier;
	private Long runCount;
	private Double timeSaving;
    @ManyToOne
    private TestTool testTool;
//    private Long toolId;


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

//    public Long getToolId() {
//        return toolId = testTool.getId();
//    }

    public Long getRunCount() {
		return runCount;
	}
	public void incrementRunCount() {
		// its a counter
		if (this.runCount == null){
			this.runCount =(long)0;	
		}
		this.runCount += 1;
	}
	public void incrementRunCountBy(Long count) {
		// its a counter
		if (this.runCount == null){
			this.runCount =(long)0;	
		}
		if (count != null){
			this.runCount += count;
		}
	}
	public Double getTimeSaving() {
		if (this.timeSaving == null){
			this.timeSaving =(Double)0.0;
		}
		return this.timeSaving;
	}
	public void setTimeSaving(double timeSaving) {
		this.timeSaving = timeSaving;
	}
	public void incrementTimeSaving(Double timeSaving) {
		// its a counter
		if (this.timeSaving == null){
			this.timeSaving =(Double)0.0;
		}
		if (timeSaving != null){
			this.timeSaving += timeSaving;
		}
	}
	public Double getTimeSavingMultiplier() {
		if (this.timeSavingMultiplier == null){
			this.timeSavingMultiplier =(Double)0.0;	
		}
		return this.timeSavingMultiplier;
	}
	public void setTimeSavingMultiplier(Double timeSavingMultiplier) {
		this.timeSavingMultiplier = timeSavingMultiplier;
	}
	public void setTotalRuntime(Long totalRuntime) {
		this.totalRuntime = totalRuntime;
	}
	public void setGeneratedDataCount(Long generatedDataCount) {
		this.generatedDataCount = generatedDataCount;
	}
	public void setTotalPass(Long totalPass) {
		this.totalPass = totalPass;
	}
	public void setTotalFail(Long totalFail) {
		this.totalFail = totalFail;
	}
	public void setTotalWarnings(Long totalWarnings) {
		this.totalWarnings = totalWarnings;
	}
	public Long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getTotalRuntime() {
		return totalRuntime;
	}
	public void incrementTotalRuntimeBy(Long totalRuntime) {
		// its a counter
		if (this.totalRuntime == null){
			this.totalRuntime =(long)0;	
		}
		if (totalRuntime != null){
			this.totalRuntime += totalRuntime;
		}
	}
	public Long getGeneratedDataCount() {
		return generatedDataCount;
	}
	public void incrementGeneratedDataCountBy(Long generatedDataCount) {
		// its a counter
		if (this.generatedDataCount == null){
			this.generatedDataCount =(long)0;	
		}
		if (generatedDataCount != null){
			this.generatedDataCount += generatedDataCount;
		}
	}
	public Long getTotalPass() {
		if (this.totalPass == null){
			this.totalPass =(long)0;	
		}
		return totalPass;
	}
	public void incrementTotalPass() {
		// its a counter
		if (this.totalPass == null){
			this.totalPass =(long)0;	
		}
		this.totalPass += 1;
	}
	public void increaseTotalPassBy(Long i) {
		if (this.totalPass == null){
			this.totalPass =(long)0;	
		}
		this.totalPass += i;
	}
	public Long getTotalFail() {
		if (this.totalFail == null){
			this.totalFail =(long)0;	
		}
		return totalFail;
	}
	public void incrementTotalFail() {
		// its a counter
		if (this.totalFail == null){
			this.totalFail =(long)0;	
		}
		this.totalFail += 1;
	}
	public void increaseTotalFailBy(Long i) {
		if (this.totalFail == null){
			this.totalFail =(long)0;	
		}
		this.totalFail += i;
	}
	public void incrementTotalWarnings() {
		// its a counter
		if (this.totalWarnings == null){
			this.totalWarnings =(long)0;	
		}
		this.totalWarnings += 1;
	}
	public void increaseTotalWarningsBy(Long i) {
		if (this.totalWarnings == null){
			this.totalWarnings =(long)0;	
		}
		this.totalWarnings += i;
	}
	public Long getTotalWarnings() {
		if (this.totalWarnings == null){
			this.totalWarnings =(long)0;	
		}
		return totalWarnings;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestCase other = (TestCase) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
        if (testTool == null) {
            if (other.testTool != null)
                return false;
        } else if (!testTool.equals(other.testTool))
            return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (timeSaving == null) {
			if (other.timeSaving != null)
				return false;
		} else if (!timeSaving.equals(other.timeSaving))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((timeSaving == null) ? 0 : timeSaving.hashCode());
        result = prime * result + ((testTool == null) ? 0 : testTool.hashCode());
		return result;
	}
}
