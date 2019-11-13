package portal.domain;

import java.util.Map;

public class MetricsContainer implements java.io.Serializable {
	
	private Map<String, Metrics> metrics;

	public Map<String, Metrics> getMetrics() {
		return metrics;
	}

	public void setMetrics(Map<String, Metrics> metrics) {
		this.metrics = metrics;
	}

}
