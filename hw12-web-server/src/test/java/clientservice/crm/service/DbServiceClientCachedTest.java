package clientservice.crm.service;

import static org.assertj.core.api.Assertions.assertThat;

import clientservice.base.AbstractHibernateTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.clientservice.model.Address;
import ru.otus.clientservice.model.Client;
import ru.otus.clientservice.model.Phone;

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

        var saved = dbServiceClient.saveClient(client);
        var loaded = dbServiceClient.getClient(saved.getId());
        assertThat(loaded).isPresent().get().usingRecursiveComparison().isEqualTo(saved);

        var forUpdate = loaded.get();
        forUpdate.setName("Vassilij");
        dbServiceClient.saveClient(forUpdate);
        var loadedUpdated = dbServiceClient.getClient(forUpdate.getId());
        assertThat(loadedUpdated).isPresent().get().usingRecursiveComparison().isEqualTo(forUpdate);
    }
}
