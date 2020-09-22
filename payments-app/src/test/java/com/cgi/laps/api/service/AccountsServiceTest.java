package com.cgi.laps.api.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import com.cgi.laps.api.dto.ServerResponse;
import com.cgi.laps.api.exception.BadRequestException;
import com.cgi.laps.api.exception.DuplicateRequestException;
import com.cgi.laps.api.model.CustomerAccount;
import com.cgi.laps.api.repository.CustomerAccountDAO;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * @author balekundrim
 *
 */
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class AccountsServiceTest {

	// @Autowired(required=true)
	// @Getter
	private static CustomerAccountDAO customerAccountDAO;

	@BeforeAll
	public static void initTest() {
		customerAccountDAO = new CustomerAccountDAO();
	}

	@Test
	@Order(1)
	public void getCustomerAccountDetailsBeginningTest() {
		List<CustomerAccount> allCustomerAccounts = customerAccountDAO.getAllAccountsDetails();
		assertTrue(allCustomerAccounts.size() == 1);
		assertTrue(allCustomerAccounts.get(0).getInitialCredit() == 25000D
				&& allCustomerAccounts.get(0).getAccountBalance() == 25000D);
	}

	@Test
	@Order(2)
	public void createCustomerAccountTest() {
		ServerResponse response = null;
		try {
			response = customerAccountDAO.createCustomerAccount(101L, "Sagar", 10000D);
		} catch (BadRequestException e) {
			e.printStackTrace();
		} catch (DuplicateRequestException e) {
			e.printStackTrace();
		}
		List<CustomerAccount> allCustomerAccounts = customerAccountDAO.getAllAccountsDetails();
		assertTrue(allCustomerAccounts.size() == 2);
		assertTrue(response.getMessage().equals("New Customer account created successfully"));
	}

	@Test
	@Order(3)
	public void getCustomerAccountDetailsTest() {
		CustomerAccount customerAccount = customerAccountDAO.getCustomerAccountDetails(101L);
		assertTrue(customerAccount.getInitialCredit() == 10000D && customerAccount.getAccountBalance() == 10000D);
	}

}
