package com.ecommerce.EcommerceApplication.Service;

import com.ecommerce.EcommerceApplication.Exceptions.UnauthorizedUserException;
import com.ecommerce.EcommerceApplication.Exceptions.UserExistsException;
import com.ecommerce.EcommerceApplication.Model.Product;
import com.ecommerce.EcommerceApplication.Model.User;
import com.ecommerce.EcommerceApplication.Repository.ProductRepository;
import com.ecommerce.EcommerceApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class EcommerceService implements EcommerceServiceInterface{
    private ProductRepository productRepository;
    private UserRepository userRepository;

    @Autowired
    public EcommerceService(ProductRepository productRepository, UserRepository userRepository){
        this.productRepository = productRepository;
        this.userRepository = userRepository;
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
        user.setEmail(updatedUser.getEmail());
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setProducts(updatedUser.getProducts());

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

        return product;
    }
/*
    /**
     * Adds product
     * @param uid
     * @param pid
     */
    /*public User addProductToCart(long uid, long pid) {
        User user = this.getUserById(uid);
        Product product = this.getProductById(pid);

        //add to cart
        user.getProducts().add(product);
        //add price to balance
        user.setBalance(user.getBalance() + product.getPrice());

        //set user in product
        product.setUser(user);

        return this.userRepository.save(user);
    }

    /**
     * Removing product
     * @param uid
     * @param pid
     */
    /*public User removeProductFromCart(long uid, long pid) {
        User user = this.getUserById(uid);
        Product product = this.getProductById(pid);
        //remove from list
        user.getProducts().remove(product);
        //deduct price from balance
        user.setBalance(user.getBalance() - product.getPrice());

        //remove user from product
        product.setUser(null);

        return this.userRepository.save(user);
    }
*/
    /**
     * remove a product
     * @param id id of product to delete
     */
    @Override
    public void deleteProduct(long id){ this.productRepository.delete(this.getProductById(id)); }

    public User checkout(long id){
        User user = this.getUserById(id);

        //clears the cart
        user.setProducts(null);
        //clears balance showing everything paid
        user.setBalance(0.0);

        return this.userRepository.save(user);
    }
}
