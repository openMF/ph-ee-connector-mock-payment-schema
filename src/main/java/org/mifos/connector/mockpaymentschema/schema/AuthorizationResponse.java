package org.mifos.connector.mockpaymentschema.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationResponse {

    private String clientCorrelationId;

    private String status;

    private String reason;
}
