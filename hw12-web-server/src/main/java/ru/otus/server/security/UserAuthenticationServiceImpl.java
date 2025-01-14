package ru.otus.server.security;

import ru.otus.server.security.userservice.DBServiceUser;
import ru.otus.server.security.userservice.User;

public class UserAuthenticationServiceImpl implements UserAuthenticationService {
    private final DBServiceUser dbServiceUser;

    public UserAuthenticationServiceImpl(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public boolean isAuthenticated(String username, String password) {
        User user = dbServiceUser.findByUsername(username).orElse(null);
        if (user == null) {
            return false;
        } else {
            return user.getPassword().equals(password);
        }
    }
}
