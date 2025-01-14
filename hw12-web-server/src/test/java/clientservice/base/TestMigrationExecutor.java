package clientservice.base;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.flyway.MigrationsExecutorFlyway;

public class TestMigrationExecutor {
    private static final Logger logger = LoggerFactory.getLogger(MigrationsExecutorFlyway.class);

    private final Flyway flyway;

    public TestMigrationExecutor(String dbUrl, String dbUserName, String dbPassword) {
        flyway = Flyway.configure()
                .dataSource(dbUrl, dbUserName, dbPassword)
                .locations("classpath:/testdb/migration")
                .load();
    }

    public void executeMigrations() {
        logger.info("db migration started...");
        flyway.migrate();
        logger.info("db migration finished.");
    }
}
