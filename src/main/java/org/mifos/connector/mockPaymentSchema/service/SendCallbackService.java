package org.mifos.connector.mockpaymentschema.service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.stereotype.Service;

@Service
public class SendCallbackService {

    public void sendCallback(String body, String callbackURL) {
        RestAssured.given().baseUri(callbackURL).contentType(ContentType.JSON).body(body).when().post();
    }
}
