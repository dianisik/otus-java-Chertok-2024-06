package ru.otus.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertySourceImpl implements PropertySource {
    private final Properties properties;

    public PropertySourceImpl(String fileName) {

        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        properties = new Properties();
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            throw new LoadPropertySourceException("Error loading property file " + fileName, e);
        }
    }

    @Override
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    @Override
    public int getIntProperty(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }
}
