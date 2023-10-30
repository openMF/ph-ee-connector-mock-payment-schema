package org.mifos.connector.mockPaymentSchema.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.camunda.zeebe.client.ZeebeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CallbackController {

    @Autowired
    private ZeebeClient zeebeClient;

    @Autowired
    ObjectMapper objectMapper;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String EXPECTED_AUTH_STATUS = "Y";


    @PostMapping("/authorization/callback")
    public ResponseEntity<Object> handleAuthorizationCallback(@RequestBody org.mifos.connector.mockpaymentschema.schema.AuthorizationResponse authResponse) throws JsonProcessingException {
        logger.info("Callback received");
        logger.debug("Auth response: {}", objectMapper.writeValueAsString(authResponse));
        Map<String, Object> variables = new HashMap<>();

        boolean isAuthorizationSuccessful = EXPECTED_AUTH_STATUS.equals(authResponse.getStatus());
        variables.put("authorizationSuccessful", isAuthorizationSuccessful);
        variables.put("clientCorrelationId", authResponse.getClientCorrelationId());
        variables.put("authorizationStatus", authResponse.getStatus());
        variables.put("authorizationFailReason", authResponse.getReason());

        if (!isAuthorizationSuccessful) {
            variables.put("approvedAmount", 0);
        }

        logger.info("Is auth successful: {}", isAuthorizationSuccessful);

        if (zeebeClient != null) {
            zeebeClient.newPublishMessageCommand()
                    .messageName("authorizationResponse")
                    .correlationKey(authResponse.getClientCorrelationId())
                    .timeToLive(Duration.ofMillis(500000))
                    .variables(variables).send();
            logger.debug("Published zeebe message event {}", "authorizationResponse");
        }
        return ResponseEntity.ok().build();
    }

}
