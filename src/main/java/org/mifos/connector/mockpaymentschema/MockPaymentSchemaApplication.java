package org.mifos.connector.mockpaymentschema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public final class MockPaymentSchemaApplication {

    private MockPaymentSchemaApplication() {}

    public static void main(String[] args) {
        SpringApplication.run(MockPaymentSchemaApplication.class, args);
    }

}
