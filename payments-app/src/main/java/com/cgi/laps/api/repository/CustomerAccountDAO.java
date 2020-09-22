package com.cgi.laps.api.repository;

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

import com.cgi.laps.api.dto.ServerResponse;
import com.cgi.laps.api.exception.BadRequestException;
import com.cgi.laps.api.exception.DuplicateRequestException;
import com.cgi.laps.api.model.CustomerAccount;
import com.cgi.laps.api.model.TransactionResult;

/**
 * CustomerAccountDAO.java: A DAO class to handle service calls to create update
 * operation calls from service class
 * 
 * @author balekundrim
 *
 */
@Component(value = "customerAccountDAO")
public final class CustomerAccountDAO {

	private static final Logger LOGGER = LogManager.getLogger(CustomerAccountDAO.class);

	private Map<Long, CustomerAccount> customerAccounts;

	private static final Long ADMIN_ACC_ID = 173627l;
	
	
	public CustomerAccountDAO() {
		super();
		createDefaultAccount();
	}

	/**
	 * Getter for customerAccounts
	 * 
	 * @return
	 */
	public Map<Long, CustomerAccount> getCustomerAccounts() {
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
	public void createDefaultAccount()  {
		try {
			this.createCustomerAccount(ADMIN_ACC_ID, "Rohit M Balekundri", 25000d);
		} catch (BadRequestException | DuplicateRequestException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Setter for customerAccounts
	 * 
	 * @param customerAccounts
	 */
	public void setCustomerAccounts(Map<Long, CustomerAccount> customerAccounts) {
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
	public ServerResponse createCustomerAccount(Long accountID, String customerName, Double initialCredit)
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
	public CustomerAccount getCustomerAccountDetails(Long accountID) {
		return (CustomerAccount) this.getCustomerAccounts().get(accountID);
	}

	/**
	 * Get all customer account details
	 * 
	 * @return
	 */
	public List<CustomerAccount> getAllAccountsDetails() {
		Iterator<Long> itrKeys = getCustomerAccounts().keySet().iterator();
		List<CustomerAccount> allAccounts = new ArrayList<>();
		while (itrKeys.hasNext()) {
			allAccounts.add(customerAccounts.get(itrKeys.next()));
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
	public ServerResponse updateCustomerAccount(Long accountID, Double balanceChange) throws BadRequestException {
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
