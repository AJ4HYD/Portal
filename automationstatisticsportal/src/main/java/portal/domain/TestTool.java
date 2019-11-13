package portal.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TestTool implements java.io.Serializable {

	@Id
    private Long id;
	private String name;
	private String description;
	private Double timeSavingMultiplier;
	private String toolClass;
	private Double timeSaving;
	private Long totalRuntime;
	private Long runCount;
	private Long generatedDataCount;
	

	public void setRunCount(Long runCount) {
		this.runCount = runCount;
	}
	public void setTotalRuntime(Long totalRuntime) {
		this.totalRuntime = totalRuntime;
	}
	public void setGeneratedDataCount(Long generatedDataCount) {
		this.generatedDataCount = generatedDataCount;
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
	public Double getTimeSavingMultiplier() {
		if (this.timeSavingMultiplier == null){
			this.timeSavingMultiplier =(double)1;	
		}
		return timeSavingMultiplier;
	}
	public void setTimeSavingMultiplier(Double timeSavingMultiplier) {
		this.timeSavingMultiplier = timeSavingMultiplier;
	}
	public Long getTotalRuntime() {
		if (this.totalRuntime == null){
			this.totalRuntime =(long)0;	
		}
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
		if (this.generatedDataCount == null){
			this.generatedDataCount =(long)0;	
		}
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
	public String getToolClass() {
		return toolClass;
	}
	public void setToolClass(String toolClass) {
		this.toolClass = toolClass;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((timeSaving == null) ? 0 : timeSaving.hashCode());
		result = prime * result + ((toolClass == null) ? 0 : toolClass.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestTool other = (TestTool) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
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
		if (toolClass == null) {
			if (other.toolClass != null)
				return false;
		} else if (!toolClass.equals(other.toolClass))
			return false;
		return true;
	}
}
