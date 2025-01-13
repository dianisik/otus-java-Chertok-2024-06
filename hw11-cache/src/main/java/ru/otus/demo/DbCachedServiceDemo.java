package ru.otus.demo;

import java.util.List;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.DbServiceClientCached;
import ru.otus.crm.service.DbServiceClientCachedImpl;
import ru.otus.crm.service.DbServiceClientImpl;

public class DbCachedServiceDemo {
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    private static final Logger log = LoggerFactory.getLogger(DbCachedServiceDemo.class);

    private static final TransactionManager transactionManager;
    private static final DataTemplate<Client> clientTemplate;
    private static final HwCache<String, Client> cache;
    private static final Client client = new Client(
            null,
            "Vasya",
            new Address(null, "AnyStreet"),
            List.of(new Phone(null, "13-555-22"), new Phone(null, "14-666-333")));

    static {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory =
                HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        transactionManager = new TransactionManagerHibernate(sessionFactory);
        clientTemplate = new DataTemplateHibernate<>(Client.class);
        cache = new MyCache<>();
    }

    public static void main(String[] args) {
        DBServiceClient clientService = new DbServiceClientImpl(transactionManager, clientTemplate);
        DbServiceClientCached cachedClientService =
                new DbServiceClientCachedImpl(transactionManager, clientTemplate, cache);
        ///
        Client savedClient = clientService.saveClient(client);
        ///
        long id = savedClient.getId();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1_000; i++) {
            clientService.getClient(id);
        }
        long end = System.currentTimeMillis();
        log.info("Non-cached 1000 reads (ms): {}", (end - start));
        ///
        start = System.currentTimeMillis();
        for (int j = 0; j < 1_000; j++) {
            cachedClientService.getClient(id);
        }
        end = System.currentTimeMillis();
        log.info("Cached 1000 reads (ms): {}", (end - start));
    }
}
