package ru.otus.server.template;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.properties.PropertySource;
import ru.otus.properties.PropertySourceImpl;

@DisplayName("Шаблонизатор должен")
class FreeMarkerProcessorTest {

    private final PropertySource propertySourceMock = mock(PropertySourceImpl.class);

    @BeforeEach
    void setUp() {
        when(propertySourceMock.getProperty("web.templates.dir")).thenReturn("/templates");
    }

    @Test
    @DisplayName("правильно обрабатывать шаблоны в папке templates")
    void renderPageTest() {
        String result = "Hello, Great User";

        FreeMarkerProcessor processor = new FreeMarkerProcessor(propertySourceMock);
        Map<String, Object> model = new HashMap<>();
        model.put("name", "Great User");
        String renderedPage = processor.renderPage("test.ftl", model);
        assertThat(renderedPage).isEqualTo(result);
    }

    @Test
    @DisplayName("выбрасывать правильный тип исключения")
    void templateProcessorExceptionTest() {
        FreeMarkerProcessor processor = new FreeMarkerProcessor(propertySourceMock);
        Map<String, Object> model = new HashMap<>();
        assertThatThrownBy(() -> processor.renderPage("wrong.name", model))
                .isInstanceOf(TemplateProcessorException.class);
    }
}
