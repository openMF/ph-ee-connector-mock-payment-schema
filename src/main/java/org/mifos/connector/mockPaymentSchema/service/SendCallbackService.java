package org.mifos.connector.mockpaymentschema.service;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SendCallbackService {

    public void sendCallback(String body, String callbackURL) {
        RequestSpecification requestSpec = new RequestSpecBuilder().build();
        requestSpec.relaxedHTTPSValidation();
        RestAssured.given(requestSpec).baseUri(callbackURL).contentType(ContentType.JSON).body(body).when().post();
        log.info("Callback sent successful: {} {}", body, callbackURL);
    }
}
