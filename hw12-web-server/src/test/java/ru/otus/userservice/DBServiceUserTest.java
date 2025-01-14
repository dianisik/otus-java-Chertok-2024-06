package ru.otus.userservice;

import static org.assertj.core.api.Assertions.*;

import clientservice.base.AbstractHibernateTest;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hibernate.repository.DataTemplateHibernate;
import ru.otus.server.security.userservice.DBServiceUser;
import ru.otus.server.security.userservice.DBServiceUserImpl;
import ru.otus.server.security.userservice.User;

@DisplayName("Сервис DBServiceUser должен")
class DBServiceUserTest extends AbstractHibernateTest {
    private final DataTemplateHibernate<User> userTemplate = new DataTemplateHibernate<>(User.class);

    @Test
    @DisplayName("находить юзера по имени")
    void methodShouldReturnCorrectUser() {
        DBServiceUser userService = new DBServiceUserImpl(transactionManager, userTemplate);
        Optional<User> user = userService.findByUsername("admin");
        assertThat(user).isPresent();
        assertThat(user.get()).usingRecursiveComparison().isEqualTo(new User(1L, "admin", "pass"));
    }
}
