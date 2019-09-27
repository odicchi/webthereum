package jp.ethereum.bean;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.crypto.Credentials;

import jp.ethereum.common.ResultType;
import jp.ethereum.common.WebthereumCommon;
import jp.ethereum.transaction.OriginalTokenTransaction;
import jp.ethereum.transaction.SendTransaction;

@Named(value = "accountInfoBean")
@ManagedBean
@RequestScoped
public class AccountInfoBean {

    // account画面用
    private Map<String, BigInteger> accountMap;
    private String address;
    private BigInteger ethBalance;
    private BigInteger nishiBalance;

    // transfer画面用
    private String toAddress;
    private String sendValue;
    private String token;

    @PostConstruct
    private void init() {

        accountMap = new HashMap<>();
        OriginalTokenTransaction orgToken = new OriginalTokenTransaction();

        List<String> accountList = WebthereumCommon.getAccountList();

        for(String address:accountList) {
            accountMap.put(address, WebthereumCommon.getBalance(address));
        }
        accountMap.remove(WebthereumCommon.BASE_ACCOUNT);

        address = WebthereumCommon.BASE_ACCOUNT;
        ethBalance = WebthereumCommon.getBalance(address);
        nishiBalance = orgToken.getBalance(address);
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

    public BigInteger getEthBalance() {
        return ethBalance;
    }

    public void setEthBalance(BigInteger ethBalance) {
        this.ethBalance = ethBalance;
    }

    public BigInteger getNishiBalance() {
        return nishiBalance;
    }

    public void setNishiBalance(BigInteger nishiBalance) {
        this.nishiBalance = nishiBalance;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public void transfer() {

        Credentials credentials = WebthereumCommon.getCredentials(WebthereumCommon.BASE_ACCOUNT);

        if(WebthereumCommon.TOKEN_KIND_ETH.equals(token)) {
            SendTransaction transfer = new SendTransaction();
            transfer.sendSignedTransaction(credentials, toAddress, BigInteger.valueOf(Long.valueOf(sendValue)));
        }else if(WebthereumCommon.TOKEN_KIND_NISHI.equals(token)) {

            OriginalTokenTransaction orgToken = new OriginalTokenTransaction();

            List<Type> args = new ArrayList<>();
            args.add(new Address(toAddress));
            args.add(new Uint(BigInteger.valueOf(Long.valueOf(sendValue))));

            orgToken.sendSignedTransaction(credentials, args, ResultType.BOOL);
        }
    }
}
