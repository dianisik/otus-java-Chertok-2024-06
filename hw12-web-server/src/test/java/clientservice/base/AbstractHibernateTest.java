package clientservice.base;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.otus.clientservice.model.Address;
import ru.otus.clientservice.model.Client;
import ru.otus.clientservice.model.Phone;
import ru.otus.clientservice.service.DBServiceClient;
import ru.otus.clientservice.service.DbServiceClientImpl;
import ru.otus.hibernate.repository.DataTemplateHibernate;
import ru.otus.hibernate.repository.HibernateUtils;
import ru.otus.hibernate.sessionmanager.TransactionManagerHibernate;
import ru.otus.server.security.userservice.User;

public abstract class AbstractHibernateTest {
    private static TestContainersConfig.CustomPostgreSQLContainer CONTAINER;
    protected SessionFactory sessionFactory;
    protected TransactionManagerHibernate transactionManager;
    protected DataTemplateHibernate<Client> clientTemplate;
    protected DBServiceClient dbServiceClient;
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    @BeforeAll
    public static void init() {
        CONTAINER = TestContainersConfig.CustomPostgreSQLContainer.getInstance();
        CONTAINER.start();
    }

    @AfterAll
    public static void shutdown() {
        CONTAINER.stop();
    }

    @BeforeEach
    public void setUp() {
        String dbUrl = System.getProperty("app.datasource.demo-db.jdbcUrl");
        String dbUserName = System.getProperty("app.datasource.demo-db.username");
        String dbPassword = System.getProperty("app.datasource.demo-db.password");

        var migrationsExecutor = new TestMigrationExecutor(dbUrl, dbUserName, dbPassword);
        migrationsExecutor.executeMigrations();

        Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
        configuration.setProperty("hibernate.connection.url", dbUrl);
        configuration.setProperty("hibernate.connection.username", dbUserName);
        configuration.setProperty("hibernate.connection.password", dbPassword);

        sessionFactory =
                HibernateUtils.buildSessionFactory(configuration, Client.class, Phone.class, Address.class, User.class);

        transactionManager = new TransactionManagerHibernate(sessionFactory);
        clientTemplate = new DataTemplateHibernate<>(Client.class);
        dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
    }

    protected EntityStatistics getUsageStatistics() {
        Statistics stats = sessionFactory.getStatistics();
        return stats.getEntityStatistics(Client.class.getName());
    }
}
