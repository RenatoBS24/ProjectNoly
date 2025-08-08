package com.projectnoly.Model.MySql;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable @AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class SalePaymentMethodId implements Serializable {
    private Integer saleId;
    private Long paymentMethodId;
}
