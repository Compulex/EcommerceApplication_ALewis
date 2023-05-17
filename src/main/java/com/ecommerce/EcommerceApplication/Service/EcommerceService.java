package com.ecommerce.EcommerceApplication.Service;

import com.ecommerce.EcommerceApplication.EcommerceApplication;
import com.ecommerce.EcommerceApplication.Exceptions.UnauthorizedUserException;
import com.ecommerce.EcommerceApplication.Exceptions.UserExistsException;
import com.ecommerce.EcommerceApplication.Model.Order;
import com.ecommerce.EcommerceApplication.Model.OrderedProduct;
import com.ecommerce.EcommerceApplication.Model.Product;
import com.ecommerce.EcommerceApplication.Model.User;
import com.ecommerce.EcommerceApplication.Repository.OrderRepository;
import com.ecommerce.EcommerceApplication.Repository.OrderedProductRepository;
import com.ecommerce.EcommerceApplication.Repository.ProductRepository;
import com.ecommerce.EcommerceApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class EcommerceService implements EcommerceServiceInterface{
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    private final OrderedProductRepository orderedProductRepository;

    @Autowired
    public EcommerceService(ProductRepository productRepository, UserRepository userRepository,
                            OrderRepository orderRepository, OrderedProductRepository orderedProductRepository){
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderedProductRepository = orderedProductRepository;
    }

    /**
     * ***************** USER methods ******************
     */

    /**
     * Adds new user - registration
     * @param user new user object
     * @return saved user object
     */
    @Override
    public User addUser(User user) throws UserExistsException {
        User findUser = this.userRepository.findByUsername(user.getUsername());
        if(findUser != null){
            throw new UserExistsException(new Date(), "Username already exists. Please choose a different one.");
        }
        else{
            EcommerceApplication.log.info("EcommerceService added user: "+ user);
            return this.userRepository.save(user);
        }

    }

    /**
     * User login - makes sure the correct username and password are passed in
     * @param user username and password
     * @return authorized user
     * @throws UnauthorizedUserException
     */
    public User loginUser(User user) throws UnauthorizedUserException {
        User findUser = this.userRepository.findByUsername(user.getUsername());

        if(findUser != null && Objects.equals(findUser.getPassword(), user.getPassword())){
            return findUser;
        }
        else{
            throw new UnauthorizedUserException(new Date(), "User or password is incorrect. Try again");
        }
    }

    /**
     * finds a user by id
     * @param id id of user
     * @return user of the id passed in
     */
    @Override
    public User getUserById(long id){
        return this.userRepository.findById(id).get();
    }


    @Override
    public List<Product> getUserCart(long id){
        User user = this.getUserById(id);
        return user.getProducts();
    }

    /**
     * updates user in the database
     * @param id id of user being updated
     * @param updatedUser user object with updated properties
     * @return updated user saved in database
     */
    @Override
    public User updateUser(long id, User updatedUser){
        User user = this.userRepository.findById(id).get();

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        user.setEmail(updatedUser.getEmail());
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setProducts(updatedUser.getProducts());
        user.setOrders(updatedUser.getOrders());

        //EcommerceApplication.log.info("EcommerceService updated user: "+ user);

        return this.userRepository.save(user);
    }

    /**
     * deletes user
     * @param id id of user being deleted
     */
    @Override
    public void deleteUser(long id){ this.userRepository.delete(this.userRepository.findById(id).get()); }


    /**
     * ***************** PRODUCT methods ******************
     */

    /**
     * Adds a new product to database
     * @param product product object
     * @return new Product
     */
    @Override
    public Product addProduct(Product product){
        //EcommerceApplication.log.info("EcommerceService added product: "+ product);
        return this.productRepository.save(product);
    }

    /**
     * returns a product by id
     * @param id
     * @return
     */
    @Override
    public Product getProductById(long id){ return this.productRepository.findById(id).get(); }

    @Override
    public List<Product>getAllProducts(){ return this.productRepository.findAll(); }

    /**
     * updates a particular product
     * @param id
     * @param updatedProduct product
     * @return
     */
    @Override
    public Product updateProduct(long id, Product updatedProduct){
        Product product = this.productRepository.findById(id).get();

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setUser(updatedProduct.getUser());
        //product.setOrderedProduct(updatedProduct.getOrderedProduct());

        EcommerceApplication.log.info("EcommerceService updated product: "+ product);

        return this.productRepository.save(product);
    }

    /**
     * remove a product
     * @param id id of product to delete
     */
    @Override
    public void deleteProduct(long id){ this.productRepository.delete(this.getProductById(id)); }


    /**
     * ***************** ORDER methods ******************
     */

    @Override
    public Order addOrder(long uid, Order order){
        User user = this.getUserById(uid);
        order.setUser(user);

        return this.orderRepository.save(order);
    }

    @Override
    public Order getOrderById(long id){
        return this.orderRepository.findById(id).get();
    }

    @Override
    public List<Order> getAllOrdersByUserId(long id){
        User user = this.getUserById(id);
        return user.getOrders();
    }

    @Override
    public Order updateOrder(long id, Order updatedOrder){
        Order order = this.orderRepository.findById(id).get();

        order.setOrderDate(updatedOrder.getOrderDate());
        order.setUser(updatedOrder.getUser());
        order.setOrderTotal(updatedOrder.getOrderTotal());
        order.setOrderedProducts(updatedOrder.getOrderedProducts());

        //EcommerceApplication.log.info("EcommerceService updated order: "+ order);

        return this.orderRepository.save(order);
    }

    @Override
    public void deleteOrder(long id){
        this.orderRepository.delete(this.getOrderById(id));
    }


    /**
     * ***************** ORDERED_PRODUCT methods ******************
     */

    @Override
    public OrderedProduct addOrderedProduct(long pid, long oid, OrderedProduct orderedProduct){
        Product product = this.getProductById(pid);
        orderedProduct.setProduct(product);

        Order order = this.getOrderById(oid);
        orderedProduct.setOrder(order);

        return this.orderedProductRepository.save(orderedProduct);
    }

    @Override
    public List<OrderedProduct> getOrderedProductsByOrder(long oid){
        return this.orderedProductRepository.findAll();
    }

    @Override
    public void deleteOrderedProduct(long id){
        OrderedProduct orderedProduct = this.orderedProductRepository.findById(id).get();
        this.orderedProductRepository.delete(orderedProduct);
    }
}
