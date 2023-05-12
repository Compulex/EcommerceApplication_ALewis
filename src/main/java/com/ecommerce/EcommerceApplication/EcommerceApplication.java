package com.ecommerce.EcommerceApplication;

import com.ecommerce.EcommerceApplication.Model.Product;
import com.ecommerce.EcommerceApplication.Repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EcommerceApplication {



	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	@Bean
	public CommandLineRunner cmd(ProductRepository productRepo){
		return args -> {
			Product product = new Product(1, "Cashews", "small bag of cashews unsalted", 0.50, null);
			productRepo.save(product);

			product = new Product(2, "Water", "16 fl oz bottle of water", 1.00, null);
			productRepo.save(product);

			product = new Product(3, "Almonds", "small bag of roasted almonds", 1.00, null);
			productRepo.save(product);

			product = new Product(4, "Sunflower Seeds - Original", "small bag of sunflower seeds", 1.50, null);
			productRepo.save(product);
		};
	}

}
