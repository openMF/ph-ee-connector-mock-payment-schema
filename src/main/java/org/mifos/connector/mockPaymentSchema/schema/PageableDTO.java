package org.mifos.connector.mockPaymentSchema.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageableDTO {

    private SortDTO sort;

    private Long pageSize;

    private Long pageNumber;

    private Long offset;

    private boolean unpaged;

    private boolean paged;
}
