package ru.otus.server.core;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.server.helper.FileSystemHelper;

@SuppressWarnings("java:S112")
public class WebServerImpl implements WebServer {
    private final Logger log = LoggerFactory.getLogger(WebServerImpl.class);

    private final Server server;
    private final ServletContextFactory servletContextFactory;

    public WebServerImpl(int port, ServletContextFactory factory) {
        server = new Server(port);
        servletContextFactory = factory;
    }

    @Override
    public void start() throws Exception {
        if (server.getHandlers().isEmpty()) {
            serverInitialize();
        }
        server.start();
        log.info("Server started");
    }

    @Override
    public void stop() throws Exception {
        server.stop();
        log.info("Server stopped");
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    private void serverInitialize() {
        ResourceHandler resourceHandler = createResourceHandler();
        Handler.Sequence handlers = new Handler.Sequence();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(servletContextFactory.getServletContextHandler());
        server.setHandler(handlers);
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirAllowed(false);
        resourceHandler.setWelcomeFiles("index.html");
        resourceHandler.setBaseResourceAsString(FileSystemHelper.localFileNameOrResourceNameToFullPath("static"));
        return resourceHandler;
    }
}
