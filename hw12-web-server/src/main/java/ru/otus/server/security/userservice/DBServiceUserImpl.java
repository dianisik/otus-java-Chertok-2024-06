package ru.otus.server.security.userservice;

import java.util.Optional;
import ru.otus.hibernate.repository.DataTemplate;
import ru.otus.hibernate.sessionmanager.TransactionManager;

public class DBServiceUserImpl implements DBServiceUser {
    private final DataTemplate<User> userDataTemplate;
    private final TransactionManager transactionManager;

    public DBServiceUserImpl(TransactionManager transactionManager, DataTemplate<User> userDataTemplate) {
        this.userDataTemplate = userDataTemplate;
        this.transactionManager = transactionManager;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return transactionManager.doInReadOnlyTransaction(
                session -> userDataTemplate.findByEntityField(session, "name", username).stream()
                        .findFirst());
    }
}
