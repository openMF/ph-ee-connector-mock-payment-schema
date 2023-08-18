package org.mifos.connector.mockpaymentschema.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.mifos.connector.mockpaymentschema.schema.AuthorizationRequest;
import org.mifos.connector.mockpaymentschema.schema.AuthorizationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class BatchService {

    @Value("${threshold.amount}")
    private String thresholdAmount;

    @Autowired
    private SendCallbackService sendCallbackService;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Async("asyncExecutor")
    public void getAuthorization(String batchId, String clientCorrelationId, AuthorizationRequest authRequest, String callbackUrl) {
        AuthorizationResponse response = new AuthorizationResponse();

        if (authRequest.getAmount().compareTo(BigDecimal.valueOf(Long.valueOf(thresholdAmount))) >= 0) {
            response.setStatus("N");
            response.setClientCorrelationId(clientCorrelationId);
            response.setReason("Error getting authorization for the request");
        } else {
            response.setClientCorrelationId(clientCorrelationId);
            response.setStatus("Y");
        }
        try {
            sendCallbackService.sendCallback(new ObjectMapper().writeValueAsString(response), callbackUrl);
        } catch (JsonProcessingException e) {
            logger.error(e.toString());
        }
    }
}
