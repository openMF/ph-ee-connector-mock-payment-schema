package org.mifos.connector.mockPaymentSchema.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.mifos.connector.mockPaymentSchema.schema.BatchDTO;
import org.mifos.connector.mockPaymentSchema.schema.BatchDetailResponse;
import org.mifos.connector.mockPaymentSchema.schema.Transfer;
import org.mifos.connector.mockPaymentSchema.schema.TransferStatus;
import org.springframework.stereotype.Service;

@Service
public class BatchServiceImpl implements BatchService {

    private List<String> requestIds;

    private List<String> workflowInstanceKeys;

    private String payerPartyId = "835322416";

    private String payeePartyId = "27713803912";

    private int successTxnCount = 9;

    public BatchServiceImpl() {
        requestIds = new ArrayList<>(Arrays.asList("f1e22fe3-9740-4fba-97b6-78f43bfa7f2f",
                "39f6ac4d052e-72aa3ea4-e6f6-4880-877f", "a27631f6-6dd4-4d69-b4fc-8932bd721913",
                "3d21e6ea-c583-44ed-b94f-af909fa7616e", "15f9a0b0-2299-436d-8433-da564140ba66",
                "f1e22fe3-9740-4fba-97b6-78f49bfa7f2f", "39f6ac4d052e-72aa3ea4-e6f6-4380-877f",
                "a27631f6-6dd4-4d69-b4fc-8452bd721913", "3d21e6ea-c583-44ed-b94f-af909fu7616e",
                "15f9a0b0-2299-436d-8433-da667140ba66"));

        workflowInstanceKeys = new ArrayList<>(Arrays.asList("1257787875372524", "3265012419947587", "9816176217118279",
                "7686876624590145", "7089769829361117", "6120480408758686", "6313003282228195", "6310718206541276",
                "3936102393207027", "6460724468126500"));
    }

    @Override
    public BatchDTO getBatchSummary(String batchId) {
        return successfulBatchSummaryResponse(batchId);
    }

    @Override
    public BatchDetailResponse getBatchDetails(String batchId, int pageNo, int pageSize) {
        List<Transfer> transactions = getTransactions(batchId);
        int toIndex = pageNo * pageSize;
        int fromIndex = toIndex - pageSize;
        toIndex = Math.min(toIndex, transactions.size());
        BatchDetailResponse batchDetailResponse = new BatchDetailResponse();
        batchDetailResponse.setContent(transactions.subList(fromIndex, toIndex));
        return batchDetailResponse;
    }

    private BatchDTO successfulBatchSummaryResponse(String batchId) {
        Long total = 10L;
        Long ongoing = 0L;
        Long failed = 1L;
        Long successful = 9L;
        BigDecimal totalAmount = BigDecimal.valueOf(100);
        BigDecimal ongoingAmount = BigDecimal.valueOf(0);
        BigDecimal failedAmount = BigDecimal.valueOf(10);
        BigDecimal successfulAmount = BigDecimal.valueOf(90);
        String status = "Pending";
        String successPercentage = "90";
        String failedPercentage = "10";

        return new BatchDTO(batchId, null, total, ongoing, failed, successful, totalAmount, successfulAmount,
                ongoingAmount, failedAmount, null, null, null, status, null, null,
                failedPercentage, successPercentage);
    }

    private List<Transfer> getTransactions(String batchId) {
        List<Transfer> transactionList = new ArrayList<>();

        for (int index = 0; index < 10; index++) {
            Transfer transfer;

            if (successTxnCount > 0) {
                transfer = getSingleTransaction(index, workflowInstanceKeys.get(index), requestIds.get(index),
                        TransferStatus.COMPLETED,
                        batchId);
            } else {
                transfer = getSingleTransaction(index, workflowInstanceKeys.get(index), requestIds.get(index),
                        TransferStatus.IN_PROGRESS,
                        batchId);
            }
            transactionList.add(transfer);
        }
        return transactionList;
    }

    private Transfer getSingleTransaction(int index, String workflowInstanceKey, String requestId, TransferStatus status,
                                          String batchId) {
        String id = String.valueOf(index);
        Date startedAt = new Date(1685536200000L);
        Date completedAt = new Date(1685536268000L);
        String payerPartyIdType = "MSISDN";
        String payeePartyIdType = "MSISDN";
        BigDecimal amount = BigDecimal.valueOf(10);
        String currency = "USD";
        String direction = "OUTGOING";

        return new Transfer(id, Long.parseLong(workflowInstanceKey), requestId, startedAt, completedAt, status,
                null, null, payeePartyId, payeePartyIdType, null, null,
                null, null, payerPartyId, payerPartyIdType, null, null,
                null, amount, currency, direction, null, batchId, null);
    }
}
