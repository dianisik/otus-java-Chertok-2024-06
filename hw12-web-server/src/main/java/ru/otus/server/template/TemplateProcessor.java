package ru.otus.server.template;

import java.util.Map;

public interface TemplateProcessor {
    String renderPage(String filename, Map<String, Object> model);
}
