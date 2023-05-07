package com.ecommerce.EcommerceApplication;

import com.ecommerce.EcommerceApplication.Model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
class EcommerceApplicationTests {

	/*@Test
	void contextLoads() {
	}*/

	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext wac;

	@BeforeEach
	void setUp(){
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}


	@Test
	void addUser() throws Exception {
		User user = new User();
		user.setFirstName("Jane");
		user.setLastName("Doe");
		user.setEmail("jdoe24@gmail.com");
		user.setUsername("jdoe24");
		user.setPassword("password");

		ObjectMapper om = new ObjectMapper();
		String inputJson = om.writeValueAsString(user);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/register")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	/**
	 * Test for incorrect login given
	 * Should return message: "User or password is incorrect. Try again"
	 * @throws Exception
	 */
	@Test
	void incorrectLogin() throws Exception {

		String inputJson = "{" + "\"username\": \"jdoe24\", " + "\"password\": \"pass\" }";

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

		String response = mvcResult.getResponse().getContentAsString();

		assertEquals("User or password is incorrect. Try again", response);
	}
}
