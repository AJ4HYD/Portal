package com.PortalDB;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Support {

    private static Properties prop = new Properties();

    private static Properties readConfigFile() throws IOException {
        FileInputStream fis = new FileInputStream("config.properties");
        prop.load(fis);
        return prop;
    }

    public static String getConfigData(String data) throws IOException {
        prop = readConfigFile();
        data = prop.getProperty(data);
        return data;
    }
}
