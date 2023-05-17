package com.ecommerce.EcommerceApplication.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private int quantity = 1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderId")
    @JsonBackReference(value = "order-op")
    private Order order;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId")
    @JsonBackReference(value = "product-op")
    private Product product;
}
