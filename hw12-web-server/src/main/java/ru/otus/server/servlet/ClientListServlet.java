package ru.otus.server.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.otus.clientservice.model.Client;
import ru.otus.clientservice.service.DBServiceClient;
import ru.otus.server.template.TemplateProcessor;

@SuppressWarnings("java:S1989")
public class ClientListServlet extends HttpServlet {
    private static final String LIST_CLIENTS_TEMPLATE = "list.ftl";

    private final transient DBServiceClient dbServiceClient;
    private final transient TemplateProcessor templateProcessor;

    public ClientListServlet(DBServiceClient dbServiceClient, TemplateProcessor templateProcessor) {
        this.dbServiceClient = dbServiceClient;
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Client> clients = dbServiceClient.findAll();
        Map<String, Object> model = new HashMap<>();
        model.put("clients", clients);
        String result = templateProcessor.renderPage(LIST_CLIENTS_TEMPLATE, model);
        resp.setContentType("text/html");
        resp.getWriter().write(result);
    }
}
