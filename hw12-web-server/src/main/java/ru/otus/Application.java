package ru.otus;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.clientservice.model.Address;
import ru.otus.clientservice.model.Client;
import ru.otus.clientservice.model.Phone;
import ru.otus.clientservice.service.DBServiceClient;
import ru.otus.clientservice.service.DbServiceClientImpl;
import ru.otus.flyway.MigrationsExecutorFlyway;
import ru.otus.hibernate.repository.DataTemplateHibernate;
import ru.otus.hibernate.repository.HibernateUtils;
import ru.otus.hibernate.sessionmanager.TransactionManager;
import ru.otus.hibernate.sessionmanager.TransactionManagerHibernate;
import ru.otus.properties.PropertySource;
import ru.otus.properties.PropertySourceImpl;
import ru.otus.server.core.ServletContextFactory;
import ru.otus.server.core.ServletContextFactoryImpl;
import ru.otus.server.core.WebServer;
import ru.otus.server.core.WebServerImpl;
import ru.otus.server.security.UserAuthenticationService;
import ru.otus.server.security.UserAuthenticationServiceImpl;
import ru.otus.server.security.userservice.DBServiceUser;
import ru.otus.server.security.userservice.DBServiceUserImpl;
import ru.otus.server.security.userservice.User;
import ru.otus.server.template.FreeMarkerProcessor;
import ru.otus.server.template.TemplateProcessor;

public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        PropertySource appProps = new PropertySourceImpl("app.properties");

        Configuration cfg = new Configuration().configure(appProps.getProperty("hibernate.config"));
        String dbUrl = cfg.getProperty("hibernate.connection.url");
        String dbUser = cfg.getProperty("hibernate.connection.username");
        String dbPassword = cfg.getProperty("hibernate.connection.password");
        new MigrationsExecutorFlyway(dbUrl, dbUser, dbPassword).executeMigrations();
        SessionFactory sf =
                HibernateUtils.buildSessionFactory(cfg, Client.class, Address.class, Phone.class, User.class);
        TransactionManager tm = new TransactionManagerHibernate(sf);

        DataTemplateHibernate<Client> clientTemplate = new DataTemplateHibernate<>(Client.class);
        DBServiceClient clientService = new DbServiceClientImpl(tm, clientTemplate);

        DataTemplateHibernate<User> userTemplate = new DataTemplateHibernate<>(User.class);
        DBServiceUser userService = new DBServiceUserImpl(tm, userTemplate);

        TemplateProcessor templateProcessor = new FreeMarkerProcessor(appProps);
        UserAuthenticationService authenticationService = new UserAuthenticationServiceImpl(userService);
        ServletContextFactory scf =
                new ServletContextFactoryImpl(clientService, authenticationService, templateProcessor);

        WebServer webServer = new WebServerImpl(appProps.getIntProperty("server.port"), scf);
        webServer.start();
        log.info("Welcome page: http://localhost:8081");
        log.info("username: 'admin', password: 'pass'");
        webServer.join();
    }
}
