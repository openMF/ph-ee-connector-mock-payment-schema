package org.mifos.connector.mockPaymentSchema.service;

import org.mifos.connector.mockPaymentSchema.schema.BatchDTO;
import org.mifos.connector.mockPaymentSchema.schema.BatchDetailResponse;

public interface BatchService {

    public BatchDTO getBatchSummary(String batchId);

    public BatchDetailResponse getBatchDetails(String batchId, int pageNo, int pageSize);
}
