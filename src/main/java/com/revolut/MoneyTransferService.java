package com.revolut;

import com.revolut.configuration.MoneyTransferConfiguration;
import com.revolut.db.dao.AccountDAO;
import com.revolut.db.dao.TransferDAO;
import com.revolut.managers.TransferManager;
import com.revolut.resources.AccountResource;
import com.revolut.resources.TransferResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Environment;
import org.flywaydb.core.Flyway;
import org.jdbi.v3.core.Jdbi;

public class MoneyTransferService extends Application<MoneyTransferConfiguration> {

    public static final String JDBC_URL = "jdbc:h2:mem:revolut;MODE=PostgreSQL;DB_CLOSE_DELAY=-1";

    public static void main(String[] args) throws Exception {
        new MoneyTransferService().run(args);
    }

    private static void initSchema() {
        Flyway flyway = Flyway.configure().dataSource(JDBC_URL, "sa", null).load();
        flyway.clean();
        flyway.migrate();
    }

    @Override
    public void run(MoneyTransferConfiguration configuration, Environment environment) throws ClassNotFoundException {
        initSchema();
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "h2");

        final AccountDAO accountDAO = jdbi.onDemand(AccountDAO.class);
        final TransferDAO transferDAO = jdbi.onDemand(TransferDAO.class);
        final TransferManager transferManager = jdbi.onDemand(TransferManager.class);
        AccountResource accountResource = new AccountResource(accountDAO, transferDAO);
        TransferResource transferResource = new TransferResource(transferDAO, transferManager);

        environment.jersey().register(accountResource);
        environment.jersey().register(transferResource);
    }
}