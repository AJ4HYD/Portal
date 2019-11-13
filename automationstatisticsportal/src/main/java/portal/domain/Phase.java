package portal.domain;

import javax.persistence.*;


@Entity
public class Phase implements java.io.Serializable {

	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String name;
    @Column(nullable = true)
	private Long phaseDataGeneratedCount;
	private Long TotalByPhaseType;


	public Long getTotalByPhaseType() {
		return TotalByPhaseType;
	}

	public void setTotalByPhaseType(Long totalByPhaseType) {
		TotalByPhaseType = totalByPhaseType;
	}

	public void incrementTotalByPhaseType(Long totalByPhaseType) {
		if (this.TotalByPhaseType == null){
			this.TotalByPhaseType =(long)0;
		}
		if (totalByPhaseType != null){
			this.TotalByPhaseType += totalByPhaseType;
		}
	}

	public void setPhaseDataGeneratedCount(Long phaseDataGeneratedCount) {
		this.phaseDataGeneratedCount = phaseDataGeneratedCount;
	}
	public Long getPhaseDataGeneratedCount() {
		return phaseDataGeneratedCount;
	}
	public void incrementPhaseDataGeneratedCountBy(Long generatedDataCount) {
		// its a counter
		if (this.phaseDataGeneratedCount == null){
			this.phaseDataGeneratedCount =(long)0;
		}
		if (generatedDataCount != null){
			this.phaseDataGeneratedCount += generatedDataCount;
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = (long) id;
	}
}
