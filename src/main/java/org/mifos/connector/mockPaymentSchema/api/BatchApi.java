package org.mifos.connector.mockPaymentSchema.api;

import org.mifos.connector.mockPaymentSchema.schema.AuthorizationRequest;
import org.mifos.connector.mockPaymentSchema.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BatchApi {

    @Autowired
    private BatchService batchService;

    @PostMapping("/batches/{batchId}")
    public ResponseEntity<Object> getAuthorization(@PathVariable String batchId,
            @RequestHeader("X-Client-Correlation-ID") String clientCorrelationId, @RequestBody AuthorizationRequest authorizationRequest,
            @RequestParam(value = "command", required = false, defaultValue = "authorize") String command,
            @RequestHeader(value = "X-CallbackURL") String callbackURL) {
        try {
            batchService.getAuthorization(batchId, clientCorrelationId, authorizationRequest, callbackURL);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
