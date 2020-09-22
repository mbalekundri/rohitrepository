package com.cgi.laps.api.web;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cgi.laps.api.dto.ServerResponse;
import com.cgi.laps.api.exception.BadRequestException;
import com.cgi.laps.api.exception.DuplicateRequestException;
import com.cgi.laps.api.exception.RecordNotFoundException;
import com.cgi.laps.api.model.CustomerAccount;
import com.cgi.laps.api.service.AccountsService;

import lombok.Getter;

/**
 * AccountsController.java: A rest controller class to manage customer account
 * create, update operations
 * 
 * @author balekundrim
 *
 */
@RestController
@RequestMapping("/accounts")
public class AccountsController {

	private static final Logger LOGGER = LogManager.getLogger(AccountsController.class);

	@Autowired
	@Getter
	private AccountsService accountsService;

	/**
	 * Find customer account details by accountID
	 * 
	 * @param accountID
	 * @return
	 * @throws RecordNotFoundException
	 */
	@GetMapping("/{accountID}")
	public ResponseEntity<CustomerAccount> getAccountByAccNo(@PathVariable("accountID") Long accountID)
			throws RecordNotFoundException {
		CustomerAccount entity = getAccountsService().getAccountByAccNo(accountID);
		LOGGER.debug("Success: Retrieve of CustomerAccount: " + entity.toString());
		return new ResponseEntity<CustomerAccount>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * Find All customer account details
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<CustomerAccount>> getAllAccountsDetails() {
		List<CustomerAccount> list = getAccountsService().getAllAccountsDetails();
		LOGGER.debug(" getAllAccountsDetails: Objects returned: " + list.size());
		return new ResponseEntity<List<CustomerAccount>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * Create new customer account and/or update balance amount if initialCredit > 0
	 * 
	 * @param account
	 * @return serverResponse
	 * @throws BadRequestException,
	 *             DuplicateRequestException
	 */
	@PostMapping
	public ResponseEntity<ServerResponse> createOrUpdateCustomerAccount(@RequestBody CustomerAccount account)
			throws BadRequestException, DuplicateRequestException {
		ServerResponse updatedResult = getAccountsService().createOrUpdateCustomerAccount(account);
		LOGGER.debug(" createOrUpdateCustomerAccount: returned: " + updatedResult);
		return new ResponseEntity<ServerResponse>(updatedResult, new HttpHeaders(), HttpStatus.CREATED);
	}

	/**
	 * Update customer account with updateAmt
	 * 
	 * @param accountID
	 * @param updateAmt
	 * @return serverResponse
	 * @throws BadRequestException
	 */
	@PutMapping
	public ResponseEntity<ServerResponse> updateCustomerAccount(@RequestParam("accountID") Long accountID,
			@RequestParam("updateAmt") Double updateAmt) throws BadRequestException {
		LOGGER.debug(" updateCustomerAccount: called: accountID: " + accountID + " updateAmt " + updateAmt);
		ServerResponse result = getAccountsService().updateCustomerAccount(accountID, updateAmt);
		LOGGER.debug(" updateCustomerAccount: returned: " + result);
		return new ResponseEntity<ServerResponse>(result, new HttpHeaders(), HttpStatus.OK);
	}

}
