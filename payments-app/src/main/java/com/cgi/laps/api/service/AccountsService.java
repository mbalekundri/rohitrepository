package com.cgi.laps.api.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgi.laps.api.dto.ServerResponse;
import com.cgi.laps.api.exception.BadRequestException;
import com.cgi.laps.api.exception.DuplicateRequestException;
import com.cgi.laps.api.exception.RecordNotFoundException;
import com.cgi.laps.api.model.CustomerAccount;
import com.cgi.laps.api.repository.CustomerAccountDAO;

import lombok.Getter;

/**
 * AccountsService.java: A service class to handle create update operation calls
 * from controller class
 * 
 * @author balekundrim
 *
 */
@Service
public final class AccountsService {

	private static final Logger LOGGER = LogManager.getLogger(AccountsService.class);

	@Autowired
	@Getter
	private CustomerAccountDAO customerAccountDAO;

	/**
	 * Get given accountID related account information
	 * 
	 * @param accountID
	 * @return
	 * @throws RecordNotFoundException
	 */
	public CustomerAccount getAccountByAccNo(Long accountID) throws RecordNotFoundException {
		CustomerAccount account = getCustomerAccountDAO().getCustomerAccountDetails(accountID);

		if (account == null) {
			LOGGER.error("No customer account exist for input accountID " + accountID);
			throw new RecordNotFoundException("No customer account exist for input accountID " + accountID);
		}
		return account;
	}

	/**
	 * 
	 * @return
	 */
	public List<CustomerAccount> getAllAccountsDetails() {
		return getCustomerAccountDAO().getAllAccountsDetails();

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
		ServerResponse result = getCustomerAccountDAO().createCustomerAccount(account.getAccountID(),
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
	public ServerResponse updateCustomerAccount(Long accountID, Double updateAmt) throws BadRequestException {
		return getCustomerAccountDAO().updateCustomerAccount(accountID, updateAmt);
	}

}
