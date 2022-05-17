package id.saputra.adi.orderservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto implements Serializable {
    private static final long serialVersionUID = 3810731400726165840L;
    private String code;
    private String name;
    private String riskProfile;
    private BigInteger balance;
    private BigInteger minAmount;
    private BigInteger maxAmount;
    private BigInteger multipleAmount;
    private boolean isDeleted;
}
