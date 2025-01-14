package ru.otus.server.security;

public interface UserAuthenticationService {
    boolean isAuthenticated(String username, String password);
}
