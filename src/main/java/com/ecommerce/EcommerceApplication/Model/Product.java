package com.ecommerce.EcommerceApplication.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userFK")
    @JsonBackReference(value = "cart")
    private User user;

    /*@OneToOne(fetch = FetchType.EAGER)
    @JsonManagedReference(value = "product-op")
    private OrderedProduct orderedProduct;*/

}
