package org.mifos.connector.mockpaymentschema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MockPaymentSchemaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockPaymentSchemaApplication.class, args);
    }

    public int hello() {
        int a = 2 + 5;
        return a;
    }

}
