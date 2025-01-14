package ru.otus.clientservice.service;

import java.util.List;
import java.util.Optional;
import ru.otus.clientservice.model.Client;

public interface DBServiceClient {
    List<Client> findAll();

    Client saveClient(Client client);

    Optional<Client> getClient(long id);
}
