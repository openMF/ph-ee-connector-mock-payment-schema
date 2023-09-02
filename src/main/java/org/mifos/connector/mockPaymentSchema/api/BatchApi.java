package org.mifos.connector.mockpaymentschema.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mifos.connector.mockpaymentschema.schema.AuthorizationRequest;
import org.mifos.connector.mockpaymentschema.service.BatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Tag(name = "GOV")
    @Operation(
            summary = "Batch Auth-z API")
    @PostMapping("/batches/{batchId}")
    public ResponseEntity<Object> getAuthorization(@PathVariable String batchId,
            @RequestHeader("X-Client-Correlation-ID") String clientCorrelationId, @RequestBody AuthorizationRequest authorizationRequest,
            @RequestParam(value = "command") String command,
            @RequestHeader(value = "X-CallbackURL") String callbackURL) {
        if(!"authorize".equals(command)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            batchService.getAuthorization(batchId, clientCorrelationId, authorizationRequest, callbackURL);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
