package id.saputra.adi.orderservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDtoRs implements Serializable {
    private static final long serialVersionUID = 111073140072616123L;
    private String errorCode;
    private String errorMessage;
    private String referenceNumber;
}
