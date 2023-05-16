package com.ecommerce.EcommerceApplication.Service;

import com.ecommerce.EcommerceApplication.Exceptions.UserExistsException;
import com.ecommerce.EcommerceApplication.Model.Order;
import com.ecommerce.EcommerceApplication.Model.OrderedProduct;
import com.ecommerce.EcommerceApplication.Model.Product;
import com.ecommerce.EcommerceApplication.Model.User;

import java.util.List;

public interface EcommerceServiceInterface {

    //User methods
    User addUser(User user) throws UserExistsException;

    User getUserById(long id);

    List<Product> getUserCart(long id);

    User updateUser(long id, User user);

    void deleteUser(long id);

    //Product methods
    Product addProduct(Product product);

    Product getProductById(long id);

    List<Product> getAllProducts();

    Product updateProduct(long id, Product product);

    void deleteProduct(long id);

    //Order methods
    Order addOrder(long id, Order order);

    Order getOrderById(long id);

    List<Order> getAllOrdersByUserId(long id);

    Order updateOrder(long id, Order order);

    void deleteOrder(long id);

    //OrderedProduct methods
    OrderedProduct addOrderedProduct(long pid, long oid, OrderedProduct orderedProduct);

    List<OrderedProduct> getOrderedProductsByOrder(long oid);

    void deleteOrderedProduct(long id);

}
