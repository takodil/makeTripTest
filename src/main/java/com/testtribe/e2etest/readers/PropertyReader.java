package com.testtribe.e2etest.readers;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    static Properties properties = new Properties();

    public PropertyReader() {
        read();
    }

    private void read() {

        String properties_file_path = "src/main/resources/config.properties";
        File file = new File(properties_file_path);

        try {
            FileReader reader = new FileReader(file);
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String propertyKey) {
        return properties.getProperty(propertyKey);
    }
}
