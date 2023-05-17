package com.ecommerce.EcommerceApplication.Controller;


import com.ecommerce.EcommerceApplication.EcommerceApplication;
import com.ecommerce.EcommerceApplication.Exceptions.UnauthorizedUserException;
import com.ecommerce.EcommerceApplication.Exceptions.UserExistsException;
import com.ecommerce.EcommerceApplication.Model.Order;
import com.ecommerce.EcommerceApplication.Model.OrderedProduct;
import com.ecommerce.EcommerceApplication.Model.Product;
import com.ecommerce.EcommerceApplication.Model.User;
import com.ecommerce.EcommerceApplication.Service.EcommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping("products")
    public List<Product> getAllProducts(){ return this.ecommerceService.getAllProducts(); }

    @PutMapping("product/{id}")
    public Product updateProduct(@PathVariable long id, @RequestBody Product product){
        return this.ecommerceService.updateProduct(id, product);
    }

    @DeleteMapping("product/{id}")
    public void deleteProduct(@PathVariable long id){ this.ecommerceService.deleteProduct(id); }

    /**
     * ********************* ORDER ENDPOINTS ***************************
     */

    @PostMapping("user/{id}/checkout")
    public ResponseEntity<Order> postOrder(@PathVariable long id, @RequestBody Order order){
        Order addedOrder = this.ecommerceService.addOrder(id, order);

        //update user by adding to list of orders
        User user = this.ecommerceService.getUserById(id);
        List<Order> orders = user.getOrders();
        orders.add(addedOrder);
        user.setOrders(orders);
        this.ecommerceService.updateUser(id, user);

        return ResponseEntity.ok(addedOrder);
    }

    @GetMapping("order/{id}")
    public Order getOrderById(@PathVariable long id){
        return this.ecommerceService.getOrderById(id);
    }

    @GetMapping("user/{id}/orders")
    public List<Order> getOrdersByUserId(@PathVariable long id){
        return this.ecommerceService.getAllOrdersByUserId(id);
    }

    @PutMapping("order/{id}")
    public Order updateOrder(@PathVariable long id, @RequestBody Order order){
        return this.ecommerceService.updateOrder(id, order);
    }

    @DeleteMapping("order/{id}")
    public void deleteOrder(@PathVariable long id){
        this.ecommerceService.deleteOrder(id);
    }


    /**
     * ********************* ORDERED_PRODUCT ENDPOINTS ***************************
     */

    @PostMapping("product/{pid}/order/{oid}/orderedProduct")
    public ResponseEntity<OrderedProduct> postOrderedProduct(@PathVariable long pid, @PathVariable long oid,
                                             @RequestBody OrderedProduct orderedProduct){
        OrderedProduct addedOP = this.ecommerceService.addOrderedProduct(pid, oid, orderedProduct);

        //order by adding list of ordered products
        Order order = this.ecommerceService.getOrderById(oid);
        List<OrderedProduct> orderOP = order.getOrderedProducts();
        orderOP.add(addedOP);
        order.setOrderedProducts(orderOP);
        this.ecommerceService.updateOrder(oid, order);

        return ResponseEntity.ok(addedOP);
    }

    @GetMapping("order/{oid}/orderedProducts")
    public List<OrderedProduct> getAllOrderedProductsByOrder(@PathVariable long oid){
        return this.ecommerceService.getOrderedProductsByOrder(oid);
    }

    @DeleteMapping("orderedProduct/{id}")
    public void deleteOrderedProduct(@PathVariable long id){
        this.ecommerceService.deleteOrderedProduct(id);
    }
}
