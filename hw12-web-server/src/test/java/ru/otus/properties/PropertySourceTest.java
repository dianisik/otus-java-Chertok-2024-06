package ru.otus.properties;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Класс PropertySource должен")
class PropertySourceTest {

    @Test
    @DisplayName("уметь читать значения свойств из файла")
    void getPropertiesTest() {
        PropertySourceImpl propertySource = new PropertySourceImpl("testapp.properties");

        assertThat(propertySource.getProperty("app.test")).isEqualTo("application");
        assertThat(propertySource.getIntProperty("app.port")).isEqualTo(5000);
    }
}
