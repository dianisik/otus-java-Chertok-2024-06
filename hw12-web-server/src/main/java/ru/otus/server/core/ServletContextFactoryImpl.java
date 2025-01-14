package ru.otus.server.core;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jetty.ee10.servlet.FilterHolder;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import ru.otus.clientservice.service.DBServiceClient;
import ru.otus.server.security.AuthenticationFilter;
import ru.otus.server.security.UserAuthenticationService;
import ru.otus.server.servlet.AddClientServlet;
import ru.otus.server.servlet.ClientListServlet;
import ru.otus.server.servlet.LoginServlet;
import ru.otus.server.template.TemplateProcessor;

public class ServletContextFactoryImpl implements ServletContextFactory {
    private static final String CLIENT_LIST = "Client list";
    private static final String NEW_CLIENT = "New client";

    private final DBServiceClient dbServiceClient;
    private final UserAuthenticationService userAuthenticationService;
    private final TemplateProcessor templateProcessor;
    private final Map<String, String> paths = new HashMap<>();

    public ServletContextFactoryImpl(
            DBServiceClient dbServiceClient,
            UserAuthenticationService userAuthenticationService,
            TemplateProcessor templateProcessor) {
        this.dbServiceClient = dbServiceClient;
        this.userAuthenticationService = userAuthenticationService;
        this.templateProcessor = templateProcessor;
        paths.put(CLIENT_LIST, "/client");
        paths.put(NEW_CLIENT, "/client/new");
    }

    @Override
    public ServletContextHandler getServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(
                new ServletHolder(new LoginServlet(templateProcessor, userAuthenticationService)), "/login");
        servletContextHandler.addServlet(
                new ServletHolder(new ClientListServlet(dbServiceClient, templateProcessor)), paths.get(CLIENT_LIST));
        servletContextHandler.addServlet(
                new ServletHolder(new AddClientServlet(dbServiceClient, templateProcessor)), paths.get(NEW_CLIENT));
        paths.values()
                .forEach(path ->
                        servletContextHandler.addFilter(new FilterHolder(new AuthenticationFilter()), path, null));
        return servletContextHandler;
    }
}
