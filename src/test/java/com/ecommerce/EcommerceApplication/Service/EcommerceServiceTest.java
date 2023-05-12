package com.ecommerce.EcommerceApplication.Service;

import com.ecommerce.EcommerceApplication.Model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class EcommerceServiceTest {
    @Mock
    private EcommerceService ecommerceService;

    @Test
    void getAllProducts(){
        Product product1 = new Product(1L, "Cashews", "small bag of cashews unsalted", 0.50, null);
        Product product2 = new Product(2L, "Water", "16 fl oz bottle of water", 1.00, null);

        when(this.ecommerceService.getAllProducts()).thenReturn(Arrays.asList(product1, product2));

        assertEquals(this.ecommerceService.getAllProducts().get(0).getName(), "Cashews");
        assertEquals(this.ecommerceService.getAllProducts().get(1).getName(), "Water");
    }

    @Test
    void deleteProduct() {
        Product product = new Product(2L, "Water", "16 fl oz bottle of water", 1.00, null);

        this.ecommerceService.deleteProduct(product.getId());

        verify(this.ecommerceService).deleteProduct(product.getId());

        assertNull(this.ecommerceService.getProductById(product.getId()));
    }
}