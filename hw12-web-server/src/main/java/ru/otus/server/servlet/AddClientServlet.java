package ru.otus.server.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import ru.otus.clientservice.model.Address;
import ru.otus.clientservice.model.Client;
import ru.otus.clientservice.model.Phone;
import ru.otus.clientservice.service.DBServiceClient;
import ru.otus.server.template.TemplateProcessor;

@SuppressWarnings("java:S1989")
public class AddClientServlet extends HttpServlet {
    private static final String ADD_NEW_CLIENT_TEMPLATE = "add.html";
    private static final String CLIENT_NAME_PARAM = "clientName";
    private static final String CLIENT_ADDRESS_PARAM = "clientAddress";
    private static final String CLIENT_PHONE_PARAM = "clientPhone";

    private final transient TemplateProcessor templateProcessor;
    private final transient DBServiceClient dbServiceClient;

    public AddClientServlet(DBServiceClient dbServiceClient, TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.getWriter().write(templateProcessor.renderPage(ADD_NEW_CLIENT_TEMPLATE, Map.of()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Client client = new Client();
        client.setName(req.getParameter(CLIENT_NAME_PARAM));
        client.setAddress(new Address(null, req.getParameter(CLIENT_ADDRESS_PARAM)));
        client.setPhones(List.of(new Phone(null, req.getParameter(CLIENT_PHONE_PARAM))));

        dbServiceClient.saveClient(client);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.sendRedirect("/client");
    }
}
