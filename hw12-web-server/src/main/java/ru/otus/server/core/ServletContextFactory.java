package ru.otus.server.core;

import org.eclipse.jetty.ee10.servlet.ServletContextHandler;

public interface ServletContextFactory {
    ServletContextHandler getServletContextHandler();
}
