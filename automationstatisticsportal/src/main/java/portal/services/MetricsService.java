package portal.services;

import java.util.Date;
import java.util.Map;

import portal.domain.Metrics;
import portal.domain.Phase;

public interface MetricsService {
	
	Map<String, Metrics> getMetricsForHomePage();
	Object[][] getToolMetricsForHomePage();
	Object[][] getPassFailMetricsForHomePage();
	Object[][] getDataTypeMetricsForHomePage();
	Object[][] getPhaseMetricsForHomePage();
	Map<String, Phase> displayPhaseMetricsForHomePage();
	Map<String, Phase> displayPhaseMetricsForHomePage(Date start, Date end);
	Object[][] getPhaseMetricsForHomePage(Date start, Date end);
	Map<String, Metrics> getMetricsForHomePage(Date start, Date end);
	Object[][] getToolMetricsForHomePage(Date start, Date end);
	Object[][] getPassFailMetricsForHomePage(Date start, Date end);
	Object[][] getDataTypeMetricsForHomePage(Date start, Date end);
}
