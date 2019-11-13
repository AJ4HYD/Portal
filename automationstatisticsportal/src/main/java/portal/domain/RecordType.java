package portal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class RecordType implements java.io.Serializable {

	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String name;
    @Column(nullable = true)
	private Long generatedDataCount;

    
	public void setGeneratedDataCount(Long generatedDataCount) {
		this.generatedDataCount = generatedDataCount;
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
