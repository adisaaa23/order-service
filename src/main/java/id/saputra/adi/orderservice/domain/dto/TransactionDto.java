package id.saputra.adi.orderservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto implements Serializable {

    private static final long serialVersionUID = -7921313200651891800L;

    private String referenceNo;
    private String username;
    private String riskProfile;
    private String productCode;
    private String productName;
    private String amount;
    private String transactionStatus;
    private String transactionDesc;
}
