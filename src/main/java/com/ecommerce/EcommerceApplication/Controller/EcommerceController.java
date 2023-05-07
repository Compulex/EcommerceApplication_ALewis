package com.ecommerce.EcommerceApplication.Controller;


import com.ecommerce.EcommerceApplication.Exceptions.UnauthorizedUserException;
import com.ecommerce.EcommerceApplication.Exceptions.UserExistsException;
import com.ecommerce.EcommerceApplication.Model.Product;
import com.ecommerce.EcommerceApplication.Model.User;
import com.ecommerce.EcommerceApplication.Service.EcommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EcommerceController {
    @Autowired
    EcommerceService ecommerceService;

    public EcommerceController(){}

    //User Endpoints
    /**
     * User should be able to register
     * localhost:9000/register
     * {
     *     "firstName": "Jane",
     *     "lastName": "Doe",
     *     "email": "jdoe24@gmail.com",
     *     "username": "jdoe24",
     *     "password": "password"
     * }
     */
    @PostMapping("register")
    public User registration(@RequestBody User user) throws UserExistsException {
        return this.ecommerceService.addUser(user);
    }

    /**
     * User should be able to log in
     * localhost:9000/login
     * {
     *     "username": "jdoe24",
     *     "password": "password"
     * }
     * @param user username with matching password
     * @return
     */
    @PostMapping("login")
    public User login(@RequestBody User user) throws UnauthorizedUserException {
        return this.ecommerceService.loginUser(user);
    }

    /**
     * User should be able to see their information
     * @param id id of logged-in user
     * @return
     */
    @GetMapping("user/{id}")
    public User getUserById(@PathVariable long id){
        return this.ecommerceService.getUserById(id);
    }

    /**
     * Returns all products in user's cart
     * @param id id of user
     * @return list of products
     */
    @GetMapping("user/{id}/products")
    public List<Product> getUserCart(@PathVariable long id){ return this.ecommerceService.getUserCart(id); }

    /**
     * User should be able to update their information
     * localhost:9000/user/{id}
     * {
     *     "firstName": "Michael",
     *     "lastName": "Jackson",
     *     "email": "mjackson5@gmail.com",
     *     "username": "mjackson5",
     *     "password": abc123
     * }
     * @param id id of user being updated
     * @param user the updated user object
     * @return updated user
     */
    @PutMapping("user/{id}")
    public User updateUser(@PathVariable long id, @RequestBody User user){
        return this.ecommerceService.updateUser(id, user);
    }

    @DeleteMapping("user/{id}")
    public void deleteUser(@PathVariable long id){
        this.ecommerceService.deleteUser(id);
    }
/*
    /**
     * Adds product to cart
     * @param uid user id
     * @param pid product id
     */
    /*@PutMapping("user/{id}/product/{pid}")
    public User postProductToCart(@PathVariable long uid, @PathVariable long pid){
        return this.ecommerceService.addProductToCart(uid, pid);
    }

    /**
     * Removes product from cart
     * @param uid user id
     * @param pid product id
     */
    /*@DeleteMapping("user/{uid}/product/{pid}")
    public User deleteProductFromCart(@PathVariable long uid, @PathVariable long pid){
        return this.ecommerceService.removeProductFromCart(uid, pid);
    }
*/

    /**
     * User paid all that was in cart
     * @param id
     */
    @PatchMapping("user/{uid}/checkout")
    public User checkout(@PathVariable long id){
        return this.ecommerceService.checkout(id);
    }


    /**
     * ********************* PRODUCT ENDPOINTS ***************************
     */

    /**
     * Adding new product
     * @param product new product object
     * @return new Product
     */
    @PostMapping("product")
    public Product postProduct(@RequestBody Product product){
        return this.ecommerceService.addProduct(product);
    }

    @GetMapping("product/{id}")
    public Product getProductById(@PathVariable long id){
        return this.ecommerceService.getProductById(id);
    }

    @PutMapping("product/{id}")
    public Product updateProduct(@PathVariable long id, @RequestBody Product product){
        return this.ecommerceService.updateProduct(id, product);
    }

    @DeleteMapping("product/{id}")
    public void deleteProduct(@PathVariable long id){ this.ecommerceService.deleteProduct(id); }


}
