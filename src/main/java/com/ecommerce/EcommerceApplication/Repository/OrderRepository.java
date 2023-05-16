package com.ecommerce.EcommerceApplication.Repository;

import com.ecommerce.EcommerceApplication.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
