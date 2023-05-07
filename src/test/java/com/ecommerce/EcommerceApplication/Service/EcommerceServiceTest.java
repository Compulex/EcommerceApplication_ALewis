package com.ecommerce.EcommerceApplication.Service;

import com.ecommerce.EcommerceApplication.Exceptions.UserExistsException;
import com.ecommerce.EcommerceApplication.Model.Product;
import com.ecommerce.EcommerceApplication.Model.User;
import com.ecommerce.EcommerceApplication.Repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EcommerceServiceTest {

    @Mock
    User user;

    @Mock
    Product product1;

    @Mock
    Product product2;

    @Mock
    EcommerceService ecommerceService;


    @Before
    public void setUp() throws UserExistsException{
        //MockitoAnnotations.openMocks(this);
        ecommerceService = mock(EcommerceService.class);
        user = mock(User.class);
        product1 = mock(Product.class);
        product2 = mock(Product.class);

        //add user
        user = new User();
        user.setId(1L);
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setEmail("jdoe24@gmail.com");
        user.setUsername("jdoe24");
        user.setPassword("password");

        product1 = new Product(1L, "Cashews", "small bag of cashews unsalted", 0.50, null);
        product2 = new Product(2L, "Water", "16 fl oz bottle of water", 1.00, null);

        //add to database
        ecommerceService.addUser(user);
        ecommerceService.addProduct(product1);
        ecommerceService.addProduct(product2);
    }

    @Test
    void addProductToCart() {
        List<Product> cart = new ArrayList<>();
        cart.add(product1);
        cart.add(product2);


        this.user.setProducts(cart);
        this.user.setBalance(product1.getPrice() + product2.getPrice());


        User ur = this.ecommerceService.getUserById(1L);

        //test that list of products is in database
        when(ur.getProducts()).thenReturn(cart);

        List<Product> userCart = this.user.getProducts();

        //test that the products are added to the list
        assertEquals(2, userCart.size());
        //test that price is added to balance
        assertEquals(this.user.getBalance(), ur.getBalance());
    }

    /*@Test
    void checkout() {


        assertEquals(0, this.user.getProducts().size());
        assertEquals(0.0, this.user.getBalance());
    }*/
}