package com.projectnoly.Model.MySql;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable @AllArgsConstructor @NoArgsConstructor
public class SalePaymentMethodId implements Serializable {
    private Long saleId;
    private Long paymentMethodId;
}
