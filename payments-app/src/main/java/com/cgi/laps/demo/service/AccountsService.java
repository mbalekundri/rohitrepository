package com.cgi.laps.demo.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgi.laps.demo.dto.ServerResponse;
import com.cgi.laps.demo.exception.BadRequestException;
import com.cgi.laps.demo.exception.DuplicateRequestException;
import com.cgi.laps.demo.exception.RecordNotFoundException;
import com.cgi.laps.demo.model.CustomerAccount;
import com.cgi.laps.demo.repository.CustomerAccountDAO;

/**
 * AccountsService.java: A service class to handle create update operation calls
 * from controller class
 * 
 * @author balekundrim
 *
 */
@Service
public class AccountsService {

	private static final Logger LOGGER = LogManager.getLogger(AccountsService.class);

	@Autowired
	CustomerAccountDAO customerAccountDAO;

	/**
	 * Get given accountID related account information
	 * 
	 * @param accountID
	 * @return
	 * @throws RecordNotFoundException
	 */
	public CustomerAccount getAccountByAccNo(String accountID) throws RecordNotFoundException {
		CustomerAccount account = customerAccountDAO.getCustomerAccountDetails(accountID);

		if (account == null) {
			LOGGER.error("No customer account exist for input accountID " + accountID);
			throw new RecordNotFoundException("No customer account exist for input accountID " + accountID);
		}
		return account;
	}

	public List<CustomerAccount> getAllAccountsDetails() {
		return customerAccountDAO.getAllAccountsDetails();

	}

	/**
	 * Create or update customer account information
	 * 
	 * @param accountc
	 * @return serverResponse
	 * @throws BadRequestException
	 */
	public ServerResponse createOrUpdateCustomerAccount(CustomerAccount account)
			throws BadRequestException, DuplicateRequestException {
		ServerResponse result = customerAccountDAO.createCustomerAccount(account.getAccountID(),
				account.getCustomerName(), account.getInitialCredit());
		LOGGER.debug(account.toString() + " updated result " + result);
		return result;
	}

	/**
	 * Update customer account with updateAmt
	 * 
	 * @param accountID
	 * @param updateAmt
	 * @return serverResponse
	 * @throws BadRequestException
	 */
	public ServerResponse updateCustomerAccount(String accountID, Double updateAmt) throws BadRequestException {
		return customerAccountDAO.updateCustomerAccount(accountID, updateAmt);
	}

}
