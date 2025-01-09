package ru.otus.crm.service;

import java.util.Optional;
import ru.otus.crm.model.Client;

public interface DbServiceClientCached {
    Client saveClient(Client client);

    Optional<Client> getClient(long id);
}
