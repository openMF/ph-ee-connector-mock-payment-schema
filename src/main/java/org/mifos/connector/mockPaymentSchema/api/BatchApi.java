package org.mifos.connector.mockpaymentschema.api;

import org.mifos.connector.mockpaymentschema.schema.AuthorizationRequest;
import org.mifos.connector.mockpaymentschema.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchApi {

    @Autowired
    private BatchService batchService;

    @PostMapping("/batches/{batchId}")
    public ResponseEntity<Object> getAuthorization(@PathVariable String batchId,
            @RequestHeader("X-Client-Correlation-ID") String clientCorrelationId, @RequestBody AuthorizationRequest authorizationRequest,
            @RequestParam(value = "command", required = false, defaultValue = "authorize") String command,
            @RequestHeader(value = "X-CallbackURL") String callbackURL) {
        if(!command.equals("authorize")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            batchService.getAuthorization(batchId, clientCorrelationId, authorizationRequest, callbackURL);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
