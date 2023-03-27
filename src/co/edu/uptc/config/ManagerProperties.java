package co.edu.uptc.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ManagerProperties {
    private final Properties properties;
    private static ManagerProperties instance = null;

    private ManagerProperties() throws Exception {
        File file = new File(GlobalConfig.PATH_PROPERTIES);
        FileInputStream fileInputStream = new FileInputStream(file);
        properties = new Properties();
        properties.load(fileInputStream);
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public static ManagerProperties getInstance() throws Exception {
        if (instance == null)
            instance = new ManagerProperties();
        return instance;
    }

}
