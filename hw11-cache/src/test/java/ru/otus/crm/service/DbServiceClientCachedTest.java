package ru.otus.crm.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.base.AbstractHibernateTest;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

@DisplayName("Кэшированный сервис должен")
class DbServiceClientCachedTest extends AbstractHibernateTest {

    @Test
    @DisplayName("Правильно сохранять, загружать и обновлять клиента")
    void shouldCorrectlySaveClient() {
        var client = new Client(
                null,
                "Vasya",
                new Address(null, "AnyStreet"),
                List.of(new Phone(null, "13-555-22"), new Phone(null, "14-666-333")));

        var saved = dbServiceClientCached.saveClient(client);
        var loaded = dbServiceClientCached.getClient(saved.getId());
        assertThat(loaded).isPresent().get().usingRecursiveComparison().isEqualTo(saved);

        var forUpdate = loaded.get();
        forUpdate.setName("Vassilij");
        dbServiceClientCached.saveClient(forUpdate);
        var loadedUpdated = dbServiceClientCached.getClient(forUpdate.getId());
        assertThat(loadedUpdated).isPresent().get().usingRecursiveComparison().isEqualTo(forUpdate);
    }
}
