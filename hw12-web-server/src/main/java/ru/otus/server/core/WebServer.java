package ru.otus.server.core;

@SuppressWarnings("java:S112")
public interface WebServer {
    void start() throws Exception;

    void stop() throws Exception;

    void join() throws Exception;
}
