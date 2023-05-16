package com.ecommerce.EcommerceApplication.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private Date orderDate;

    @Column
    private double orderTotal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "user-order")
    @JoinColumn(name="userFK")
    private User user;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonManagedReference(value = "order-op")
    private List<OrderedProduct> orderedProducts;
}
