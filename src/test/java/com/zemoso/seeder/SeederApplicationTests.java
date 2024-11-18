package com.zemoso.seeder;

import com.zemoso.seeder.controller.CashkickController;
import com.zemoso.seeder.controller.ContractController;
import com.zemoso.seeder.controller.PaymentController;
import com.zemoso.seeder.controller.UserController;
import com.zemoso.seeder.service.CashkickService;
import com.zemoso.seeder.service.ContractService;
import com.zemoso.seeder.service.PaymentService;
import com.zemoso.seeder.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class SeederApplicationTests {

	@Autowired
	private UserController userController;

	@Autowired
	private PaymentController paymentController;

	@Autowired
	private ContractController contractController;

	@Autowired
	private CashkickController cashkickController;

	@Autowired
	private UserService userService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private ContractService contractService;

	@Autowired
	private CashkickService cashkickService;

	@Test
	void contextLoads() {
		assertThat(userController).isNotNull();
		assertThat(paymentController).isNotNull();
		assertThat(contractController).isNotNull();
		assertThat(cashkickController).isNotNull();

		assertThat(userService).isNotNull();
		assertThat(paymentService).isNotNull();
		assertThat(contractService).isNotNull();
		assertThat(cashkickService).isNotNull();
	}

}
