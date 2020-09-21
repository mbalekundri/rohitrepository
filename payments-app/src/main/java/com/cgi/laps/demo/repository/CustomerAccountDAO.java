package com.cgi.laps.demo.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cgi.laps.demo.dto.ServerResponse;
import com.cgi.laps.demo.exception.BadRequestException;
import com.cgi.laps.demo.exception.DuplicateRequestException;
import com.cgi.laps.demo.model.CustomerAccount;
import com.cgi.laps.demo.model.TransactionResult;

/**
 * CustomerAccountDAO.java: A DAO class to handle service calls to create update
 * operation calls from service class
 * 
 * @author balekundrim
 *
 */
@Component
public class CustomerAccountDAO {

	private static final Logger LOGGER = LogManager.getLogger(CustomerAccountDAO.class);

	private Map<String, CustomerAccount> customerAccounts;

	private static final String ADMIN_ACC_ID = "173627";

	/**
	 * Getter for customerAccounts
	 * 
	 * @return
	 */
	public Map<String, CustomerAccount> getCustomerAccounts() {
		if (customerAccounts == null) {
			customerAccounts = Collections.synchronizedMap(new HashMap<>());
		}
		return customerAccounts;
	}

	/**
	 * By default create one admin account information during startup with some
	 * values
	 * 
	 * @throws BadRequestException,
	 *             DuplicateRequestException
	 */
	@PostConstruct
	public void createDefaultAccount() throws BadRequestException, DuplicateRequestException {
		this.createCustomerAccount(ADMIN_ACC_ID, "Rohit M Balekundri", 25000d);
	}

	/**
	 * Setter for customerAccounts
	 * 
	 * @param customerAccounts
	 */
	public void setCustomerAccounts(Map<String, CustomerAccount> customerAccounts) {
		this.customerAccounts = customerAccounts;
	}

	/**
	 * Create a customer account. Once the end point is called, a new account will
	 * be created. Also, if initialCredit is not 0, a transaction will be sent to
	 * the new account.
	 * 
	 * @param accountID
	 * @param customerName
	 * @param initialCredit
	 * @return ServerResponse
	 * @throws BadRequestException,
	 *             DuplicateRequestException
	 */
	public ServerResponse createCustomerAccount(String accountID, String customerName, Double initialCredit)
			throws BadRequestException, DuplicateRequestException {
		ServerResponse response = null;
		if (!StringUtils.isEmpty(customerName)) {
			// Validation: Creating account with duplicate accountID should not be allowed
			if (this.getCustomerAccounts().containsKey(accountID)) {
				LOGGER.error("Duplicate record: Customer account already exists " + accountID);
				// Throw exception that it duplicate accountID should not be allowed
				throw new DuplicateRequestException("Duplicate record: Customer account already exists " + accountID);
			} else {
				this.getCustomerAccounts().put(accountID, new CustomerAccount(accountID, customerName));
				LOGGER.debug("New Customer account created successfully " + accountID);
				response = new ServerResponse(TransactionResult.SUCCESS, "New Customer account created successfully",
						null);
			}

			if (intialCreditNotZero(initialCredit)) {
				this.updateCustomerAccount(accountID, initialCredit);
				this.getCustomerAccounts().get(accountID).setInitialCredit(initialCredit);
			}
		} else {
			LOGGER.error("Invalid customer name " + accountID);
			throw new BadRequestException("Invalid customer name " + accountID);
		}
		response.getDetails().add("New Customer account: " + this.getCustomerAccounts().get(accountID).toString());
		LOGGER.debug("New Customer account: " + this.getCustomerAccounts().get(accountID).toString());
		return response;
	}

	/**
	 * Check for intial credit value is not zero
	 * 
	 * @param initialCredit
	 * @return
	 */
	private boolean intialCreditNotZero(Double initialCredit) {
		return initialCredit > 0d;
	}

	/**
	 * Get customer account for specific customer using accountID
	 * 
	 * @param accountID
	 * @return
	 */
	public CustomerAccount getCustomerAccountDetails(String accountID) {
		return (CustomerAccount) this.getCustomerAccounts().get(accountID);
	}

	/**
	 * Get all customer account details
	 * 
	 * @return
	 */
	public List<CustomerAccount> getAllAccountsDetails() {
		Iterator<String> itrKeys = getCustomerAccounts().keySet().iterator();
		List<CustomerAccount> allAccounts = new ArrayList<>();
		// Using synchronized block is advisable
		synchronized (customerAccounts) {
			while (itrKeys.hasNext()) {
				allAccounts.add(customerAccounts.get(itrKeys.next()));
			}
		}
		return allAccounts;
	}

	/**
	 * Update customer account with new value balanceChange (credit/debit)
	 * 
	 * @param accountID
	 * @param balanceChange
	 * @return serverResponse
	 * @throws BadRequestException
	 */
	public ServerResponse updateCustomerAccount(String accountID, Double balanceChange) throws BadRequestException {
		CustomerAccount account = (CustomerAccount) this.getCustomerAccounts().get(accountID);
		ServerResponse serverResponse = new ServerResponse();
		if (account == null) {
			LOGGER.error("No customer account exist for input accountID " + accountID);
			throw new BadRequestException("No customer account exist for input accountID " + accountID);
		}
		serverResponse.getDetails().add("old value: " + account.getAccountBalance());
		Double updatedAmt = account.updateAccountBalance(balanceChange);
		serverResponse.getDetails().add("new value: " + updatedAmt);
		serverResponse.setMessage("Customer account new balance amount: " + updatedAmt);
		serverResponse.setResult(TransactionResult.SUCCESS);
		return serverResponse;
	}

}
