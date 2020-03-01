package com.revolut;

import com.revolut.configuration.MoneyTransferConfiguration;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.BeforeClass;
import org.junit.ClassRule;

public class Scenario {
    @ClassRule
    public static final DropwizardAppRule<MoneyTransferConfiguration> RULE =
            new DropwizardAppRule<>(MoneyTransferService.class, ResourceHelpers.resourceFilePath("test-money-transfer-configuration.yml"));

    protected static String baseUrl;

    @BeforeClass
    public static void setUp(){
        baseUrl = String.format("http://localhost:%d/api/", RULE.getLocalPort());
    }

}
