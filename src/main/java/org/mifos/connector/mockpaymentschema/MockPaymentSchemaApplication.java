package org.mifos.connector.mockpaymentschema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@EnableCaching
@SpringBootApplication
@ComponentScan("org.mifos.connector.mockPaymentSchema")
public class MockPaymentSchemaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockPaymentSchemaApplication.class, args);
    }


}
