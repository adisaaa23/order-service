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
public class OrderDtoRq implements Serializable {
    private static final long serialVersionUID = 3810731400726165840L;
    private String username;
    private String productCode;
    private String amount;
    private String referenceNumber; //optional
}
