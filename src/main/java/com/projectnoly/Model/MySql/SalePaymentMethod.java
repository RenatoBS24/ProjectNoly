package com.projectnoly.Model.MySql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "sale_payment_method")
public class SalePaymentMethod {
    @EmbeddedId
    private SalePaymentMethodId id;
    @ManyToOne
    @MapsId("saleId")
    @JoinColumn(name = "id_sale")
    private Sale sale;
    @ManyToOne
    @MapsId("paymentMethodId")
    @JoinColumn(name = "id_payment_method")
    private PaymentMethod paymentMethod;
    private Double total;
}
