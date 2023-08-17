package org.mifos.connector.mockPaymentSchema.api;

import org.mifos.connector.mockPaymentSchema.schema.BatchDTO;
import org.mifos.connector.mockPaymentSchema.schema.BatchDetailResponse;
import org.mifos.connector.mockPaymentSchema.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batches")
public class BatchApi {

    @Autowired
    private BatchService batchService;

    @GetMapping("/{batchId}}/summary")
    public BatchDTO batchSummary(@PathVariable String batchId) {
        return batchService.getBatchSummary(batchId);
    }

    @GetMapping("/{batchId}/detail")
    public BatchDetailResponse batchDetail(@PathVariable String batchId, @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return batchService.getBatchDetails(batchId, pageNo, pageSize);
    }

}
