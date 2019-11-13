package portal.domain;


public class Metrics implements java.io.Serializable {

	private Long TotalTestRuns;
	private Long TotalTools;
	private Long TotalDataItemsGenerated;
	private Long TotalTestCases;
	private Long TotalRuntime;
	private Double TotalRuntimeSaved;

	public Long getTotalTestRuns() {
		return TotalTestRuns;
	}
	public void setTotalTestRuns(Long totalTestRuns) {
		TotalTestRuns = totalTestRuns;
	}
	public void incrementTotalTestRuns(Long totalTestRuns) {
		if (this.TotalTestRuns == null){
			this.TotalTestRuns =(long)0;	
		}
		if (totalTestRuns != null){
			this.TotalTestRuns += totalTestRuns;
		}
	}
	public Long getTotalTools() {
		return TotalTools;
	}
	public void setTotalTools(Long totalTools) {
		TotalTools = totalTools;
	}
	public void incrementTotalTools(Long totalTools) {
		if (this.TotalTools == null){
			this.TotalTools =(long)0;	
		}
		if (totalTools != null){
			this.TotalTools += totalTools;
		}
	}
	public Long getTotalDataItemsGenerated() {
		return TotalDataItemsGenerated;
	}
	public void setTotalDataItemsGenerated(Long totalDataItemsGenerated) {
		TotalDataItemsGenerated = totalDataItemsGenerated;
	}
	public void incrementTotalDataItemsGenerated(Long totalDataItemsGenerated) {
		if (this.TotalDataItemsGenerated == null){
			this.TotalDataItemsGenerated =(long)0;	
		}
		if (totalDataItemsGenerated != null){
			this.TotalDataItemsGenerated += totalDataItemsGenerated;
		}
	}
	public Long getTotalTestCases() {
		return TotalTestCases;
	}
	public void setTotalTestCases(Long totalTestCases) {
		TotalTestCases = totalTestCases;
	}
	public void incrementTotalTestCases(Long totalTestCases) {
		if (this.TotalTestCases == null){
			this.TotalTestCases =(long)0;	
		}
		if (totalTestCases != null){
			this.TotalTestCases += totalTestCases;
		}
	}
	public Long getTotalRuntime() {
		return TotalRuntime;
	}
	public void setTotalRuntime(Long totalRuntime) {
		TotalRuntime = totalRuntime;
	}
	public void incrementTotalRuntime(Long totalRuntime) {
		if (this.TotalRuntime == null){
			this.TotalRuntime =(long)0;	
		}
		if (totalRuntime != null){
			this.TotalRuntime += totalRuntime;
		}
	}
	public Double getTotalRuntimeSaved() {
		if (TotalRuntimeSaved!= null)
		return TotalRuntimeSaved;
		else{
			return (Double)0.0;
		}
	}
	public void setTotalRuntimeSaved(Double totalRuntimeSaved) {
		TotalRuntimeSaved = totalRuntimeSaved;
	}
	public void incrementTotalRuntimeSaved(Double totalRuntimeSaved) {
		if (this.TotalRuntimeSaved == null){
			this.TotalRuntimeSaved =(Double)0.0;	
		}
		if (totalRuntimeSaved != null){
			this.TotalRuntimeSaved += totalRuntimeSaved;
		}
	}

	
}
