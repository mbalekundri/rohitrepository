package com.cgi.laps.demo.model;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cgi.laps.demo.exception.BadRequestException;

/**
 * CustomerAccount.java: A model class to store customer account information
 * 
 * @author balekundrim
 *
 */
public class CustomerAccount implements Serializable {

	private static final Logger LOGGER = LogManager.getLogger(CustomerAccount.class);

	private static final long serialVersionUID = 1L;

	private String accountID;
	private String customerName;
	private Double initialCredit = 0d;
	private Double accountBalance = 0d;

	public CustomerAccount() {
		super();
	}

	public CustomerAccount(String accountID, String customerName) {
		this.accountID = accountID;
		this.customerName = customerName;
	}

	public CustomerAccount(String accountID, String customerName, Double initialCredit) {
		super();
		this.accountID = accountID;
		this.customerName = customerName;
		this.initialCredit = initialCredit;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Double getInitialCredit() {
		return initialCredit;
	}

	public void setInitialCredit(Double initialCredit) {
		this.initialCredit = initialCredit;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	/**
	 * Update account balance with input value
	 * 
	 * @param balanceChange
	 * @return
	 * @throws BadRequestException
	 */
	public Double updateAccountBalance(Double balanceChange) throws BadRequestException {
		if (this.accountBalance + balanceChange < 0d) {
			LOGGER.error(" Negative amount calculated: Reverted " + (this.accountBalance + balanceChange));
			throw new BadRequestException("Negative account balance < 0 should not be allowed");
		}
		this.accountBalance += balanceChange;
		LOGGER.debug(" New balance amount recorded: " + this.accountBalance);
		return this.accountBalance;
	}

	@Override
	public String toString() {
		return "CustomerAccount [accountID=" + accountID + ", customerName=" + customerName + ", initialCredit="
				+ initialCredit + ", accountBalance=" + accountBalance + "]";
	}

}