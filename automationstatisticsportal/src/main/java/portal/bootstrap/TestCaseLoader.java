package portal.bootstrap;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import portal.domain.*;
import portal.repositories.*;

@Component
@Configuration
@PropertySource("classpath:application.yml")
public class TestCaseLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    Environment env;

	private TestCaseRepository testCaseRepository;
	private RecordTypeRepository recordTypeRepository;
	private TestToolRepository testToolRepository;
	private PhaseRepository phaseRepository;
	
	@Autowired
	public void setTestRunRepository(TestCaseRepository testCaseRepository)  {
		this.testCaseRepository = testCaseRepository;
	}

	@Autowired
	public void setPhaseRepository(PhaseRepository phaseRepository) {
		this.phaseRepository = phaseRepository;
	}

	@Autowired
	public void setRecordTypRepository(RecordTypeRepository recordTypeRepository)  {
		this.recordTypeRepository = recordTypeRepository;
	}
	@Autowired
	public void setTestToolRepository(TestToolRepository testToolRepository)  {
		this.testToolRepository = testToolRepository;
	}
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		String DATA_LOAD = env.getProperty("load-files");

		// ensure the required record types exist
		if (phaseRepository.count() <5){
			Phase phase = new Phase();
			phase.setId(1);
			phase.setName("Preparation");
			phaseRepository.save(phase);
			phase.setId(2);
			phase.setName("Execution");
			phaseRepository.save(phase);
			phase.setId(3);
			phase.setName("Test Data");
			phaseRepository.save(phase);
			phase.setId(4);
			phase.setName("Reporting");
			phaseRepository.save(phase);
			phase.setId(5);
			phase.setName("Cloud");
			phaseRepository.save(phase);
			phase.setId(6);
			phase.setName("PMO");
			phaseRepository.save(phase);
		}

		// ensure the required phases exist
		if (recordTypeRepository.count() <7){
			RecordType recordType = new RecordType();
			recordType.setId(1);
			recordType.setName("Database Record");
			recordTypeRepository.save(recordType);
			recordType.setId(2);
			recordType.setName("XML File");
			recordTypeRepository.save(recordType);
			recordType.setId(3);
			recordType.setName("JSON File");
			recordTypeRepository.save(recordType);
			recordType.setId(4);
			recordType.setName("Enroll User For Service");
			recordTypeRepository.save(recordType);
			recordType.setId(5);
			recordType.setName("ETMP Flat File");
			recordTypeRepository.save(recordType);
			recordType.setId(6);
			recordType.setName("EDI File");
			recordTypeRepository.save(recordType);
			recordType.setId(7);
			recordType.setName("GFF File");
			recordTypeRepository.save(recordType);
			recordType.setId(8);
			recordType.setName("Print to Mail test case");
			recordTypeRepository.save(recordType);
		}

		// load the clients existing data from CSV 
		try {
			CsvLoader loader = new CsvLoader();
			//  test tools
			Path dataFile = Paths.get(DATA_LOAD, "testTools.csv");
			List<Map<String,String>> entries = loader.getEntries(dataFile.toString());
			console(entries);

			if (!entries.isEmpty()){
				for (Map<String,String> entry : entries){
					long id =Long.parseLong(entry.get("id"));
					TestTool existingtestTool=testToolRepository.findOne(id);
					if (existingtestTool == null){
						TestTool testTool = new TestTool();
						testTool.setId(id);
						testTool.setName(entry.get("name"));
						testTool.setDescription(entry.get("description"));
						testTool.setTimeSavingMultiplier(Double.parseDouble(entry.get("timesaving")));
						testTool.setToolClass(entry.get("toolClass"));
						testToolRepository.save(testTool);
						System.out.println(testTool);
					}else{
						Boolean isUpdated=false;

						if(existingtestTool.getName()!=null && !existingtestTool.getName().equals(entry.get("name"))){
							existingtestTool.setName(entry.get("name"));
							isUpdated=true;
						}

						if(existingtestTool.getDescription()!=null && !existingtestTool.getDescription().equals(entry.get("description"))){
							existingtestTool.setDescription(entry.get("description"));
							isUpdated=true;
						}

						if (!entry.get("timesaving").isEmpty()){
							Double timesaving=Double.parseDouble(entry.get("timesaving"));

							if(existingtestTool.getTimeSavingMultiplier()==null || !existingtestTool.getTimeSavingMultiplier().equals(timesaving)){
								existingtestTool.setTimeSavingMultiplier(timesaving);
								isUpdated=true;
							}

						}

						if(existingtestTool.getToolClass()!=null && !existingtestTool.getToolClass().equals(entry.get("toolClass"))){
							existingtestTool.setDescription(entry.get("toolClass"));
							isUpdated=true;
						}
						if(isUpdated){
							testToolRepository.save(existingtestTool);
						}
					}
				}
			}
            entries.clear();

            // test cases
            dataFile = Paths.get(DATA_LOAD, "testCases.csv");
            entries = loader.getEntries(dataFile.toString());
            console(entries);

			if (!entries.isEmpty()){
				for (Map<String,String> entry : entries){
					long id =Long.parseLong(entry.get("id"));
					TestCase existingtestcase=testCaseRepository.findOne(id);
                    TestTool testTool = testToolRepository.findOne(Long.valueOf(entry.get("toolId")));
					if (existingtestcase == null){
						TestCase testCase = new TestCase();
						testCase.setId(id);
						testCase.setName(entry.get("name"));
						testCase.setDescription(entry.get("description"));
						if (!entry.get("timesaving").isEmpty()){
							testCase.setTimeSavingMultiplier(Double.parseDouble(entry.get("timesaving")));
						}
                        testCase.setTestTool(testTool);
   				        testCaseRepository.save(testCase);
						System.out.println(testCase);
					}
					else{
						Boolean isUpdated=false;

						if(existingtestcase.getName()!=null && !existingtestcase.getName().equals(entry.get("name"))){
							existingtestcase.setName(entry.get("name"));
							isUpdated=true;
						}

						if(existingtestcase.getDescription()!=null && !existingtestcase.getDescription().equals(entry.get("description"))){
							existingtestcase.setDescription(entry.get("description"));
							isUpdated=true;
						}

						if (!entry.get("timesaving").isEmpty()){
							Double timesaving=Double.parseDouble(entry.get("timesaving"));

							if(existingtestcase.getTimeSavingMultiplier()==null || !existingtestcase.getTimeSavingMultiplier().equals(timesaving)){
								existingtestcase.setTimeSavingMultiplier(timesaving);
								isUpdated=true;
							}

						}

                        if(existingtestcase.getTestTool().getId()!=null && !existingtestcase.getTestTool().getId().equals(Long.valueOf(entry.get("toolId")))){
                            existingtestcase.setTestTool(testToolRepository.findOne(Long.valueOf(entry.get("toolId"))));
                            isUpdated=true;
                        }
						if(isUpdated){
							testCaseRepository.save(existingtestcase);
						}

					}
				}
			}
			entries.clear();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void console(List<Map<String,String>> entries){
		for (Map<String,String> e : entries){
			System.out.println(Arrays.toString(e.entrySet().toArray()));
		}
		System.out.println();
	}

}
