package portal.bootstrap;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;

public class CsvLoader {
	
	private static String[] columnHeaders;
	private static CSVReader reader;
	private static List<Map<String,String>> entries = new ArrayList<Map<String, String>>();
	
	public List<Map<String,String>> getEntries(String filePath) throws Exception{
		entries.clear();
		File f = new File(filePath.replace("%UserProfile%", System.getProperty("user.home")));
		if (f.exists() && f.isFile() && f.length() > 0){
			reader = new CSVReader(new FileReader(f));
			columnHeaders = reader.readNext();  
			
			String [] rowData;
			while ((rowData = reader.readNext()) != null) {
				if (columnHeaders.length == rowData.length){
					Map<String,String> vals = new HashMap<String,String>();
					for(int i = 0; i < columnHeaders.length; i++)
					{
						vals.put(columnHeaders[i], rowData[i]);
					}
					entries.add(vals);
				}
			}
			reader.close();
		}
		return entries;
	}

}

