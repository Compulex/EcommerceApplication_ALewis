package com.ecommerce.EcommerceApplication.Repository;

import com.ecommerce.EcommerceApplication.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
