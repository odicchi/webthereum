package jp.ethereum.bean;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

import org.web3j.crypto.Credentials;

import jp.ethereum.common.WebthereumCommon;
import jp.ethereum.transaction.SendTransaction;

@Named(value = "accountInfoBean")
@ManagedBean
@SessionScoped
public class AccountInfoBean {

	private Map<String, BigInteger> accountMap;


	private String address;
	private BigInteger balance;

	private String toAddress;
	private String sendValue;

	@PostConstruct
	private void init() {

		accountMap = new HashMap<>();

		List<String> accountList = WebthereumCommon.getAccountList();

		for(String address:accountList) {
			accountMap.put(address, WebthereumCommon.getBalance(address));
		}

		address = WebthereumCommon.BASE_ACCOUNT;
		balance = WebthereumCommon.getBalance(address);
	}

	public Map<String, BigInteger> getAccountMap() {
		return accountMap;
	}

	public void setAccountMap(Map<String, BigInteger> map) {
		accountMap = map;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigInteger getBalance() {
		return balance;
	}

	public void setBalance(BigInteger balance) {
		this.balance = balance;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getSendValue() {
		return sendValue;
	}

	public void setSendValue(String sendValue) {
		this.sendValue = sendValue;
	}


	public void transfer() {

		Credentials credentials = WebthereumCommon.getCredentials(WebthereumCommon.BASE_ACCOUNT);

		SendTransaction transfer = new SendTransaction();
		transfer.sendSignedTransaction(credentials, toAddress, BigInteger.valueOf(Long.valueOf(sendValue)));
	}
}
