package com.ecommerce.EcommerceApplication.Repository;

import com.ecommerce.EcommerceApplication.Model.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Long> {
}
