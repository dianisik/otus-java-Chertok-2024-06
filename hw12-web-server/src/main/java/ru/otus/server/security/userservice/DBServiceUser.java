package ru.otus.server.security.userservice;

import java.util.Optional;

public interface DBServiceUser {
    Optional<User> findByUsername(String username);
}
