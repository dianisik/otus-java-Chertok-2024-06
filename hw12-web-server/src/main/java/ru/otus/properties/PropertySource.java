package ru.otus.properties;

public interface PropertySource {
    String getProperty(String key);

    int getIntProperty(String key);
}
