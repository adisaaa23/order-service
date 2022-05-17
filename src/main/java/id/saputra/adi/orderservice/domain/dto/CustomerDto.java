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
public class CustomerDto implements Serializable {
    private static final long serialVersionUID = 3810731400726165840L;
    private String username;
    private String fullName;
    private String riskProfileCode;
    private boolean isDeleted;
}
