package ru.otus.server.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import ru.otus.properties.PropertySource;

public class FreeMarkerProcessor implements TemplateProcessor {
    private final Configuration configuration;

    public FreeMarkerProcessor(PropertySource propertySource) {
        String templatesPath = propertySource.getProperty("web.templates.dir");
        configuration = new Configuration(Configuration.VERSION_2_3_33);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(FreeMarkerProcessor.class, templatesPath);
    }

    @Override
    public String renderPage(String filename, Map<String, Object> model) {
        try (Writer writer = new StringWriter()) {
            Template template = configuration.getTemplate(filename);
            template.process(model, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new TemplateProcessorException("Error processing template " + filename, e);
        }
    }
}
