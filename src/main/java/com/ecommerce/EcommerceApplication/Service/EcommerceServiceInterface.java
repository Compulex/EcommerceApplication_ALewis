package com.ecommerce.EcommerceApplication.Service;

import com.ecommerce.EcommerceApplication.Exceptions.UserExistsException;
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

    //Product
    Product addProduct(Product product);

    Product getProductById(long id);

    Product updateProduct(long id, Product product);

    void deleteProduct(long id);

}
