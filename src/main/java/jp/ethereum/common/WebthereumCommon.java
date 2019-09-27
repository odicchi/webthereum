package jp.ethereum.common;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionObject;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;
import org.web3j.protocol.http.HttpService;

public class WebthereumCommon {

	public static final Admin web3j = Admin.build(new HttpService("http://192.168.33.11:30302"));

	private static final String PRIV_KEY_PATH = "C:\\Users\\yuki_nishiyama\\Documents\\12_sample_wallet\\key-store\\";
	private static final String PASSWORD = "infomartpw";

	public static final String BASE_ACCOUNT = "0x4e90147dcdd6f6aed51714c9de4eae3899678c75";

	public static final String TOKEN_KIND_ETH = "eth";
	public static final String TOKEN_KIND_NISHI = "NISHI";

	public static List<String> getAccountList(){

		List<String> list = null;
		try{
			list =web3j.ethAccounts().send().getAccounts();
		}catch(IOException e) {

		}
		return list;
	}

	public static BigInteger getBalance(String address) {

		BigInteger balance = null;
		try {
			balance =web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().getBalance();
		}catch(IOException e) {
			e.printStackTrace();
		}

		return balance;
	}

	public static Credentials getCredentials(String address) {

		File dir = new File(PRIV_KEY_PATH);
		File[] files = dir.listFiles();

		String fileName = null;
		String searchAddress = address;
		if(address.substring(0, 2).equals("0x")) {
			searchAddress = address.substring(2);
		}

		for(File file:files) {
			if(file.getName().contains(searchAddress)) {
				fileName = file.getName();
				break;
			}
		}

		Credentials credentials = null;
		try {
			if(fileName != null) {

				credentials = WalletUtils.loadCredentials(PASSWORD, PRIV_KEY_PATH + fileName);
			}
		}catch(IOException | CipherException e) {
			e.printStackTrace();
		}
		return credentials;
	}

	public static List<EthBlock> getBlock() {

		List<EthBlock> blockList = null;
		try {
			EthBlock latestBlock = web3j.ethGetBlockByNumber(
					DefaultBlockParameterName.LATEST,
					false).send();

			blockList = new ArrayList<>();
			Block block = latestBlock.getResult();
			BigInteger latestBlockNumber = block.getNumber();

			for(int i=0; i < 10; i++) {
				blockList.add(web3j.ethGetBlockByNumber(
						DefaultBlockParameter.valueOf(latestBlockNumber.add(BigInteger.valueOf(- i))),
						true).send());
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		return blockList;
	}

	public static EthBlock getBlock(BigInteger blockNumber) {

		EthBlock block = null;
		try {
			block =web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(blockNumber), true).send();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return block;
	}

	public static List<TransactionObject> getTransactionList(BigInteger blockNumber) {

		List<TransactionObject> transactionList = new ArrayList<>();
		try {
			List<TransactionResult> txs = web3j.ethGetBlockByNumber(new DefaultBlockParameterNumber(blockNumber), true).send().getBlock().getTransactions();
			for(TransactionResult result: txs) {
				transactionList.add((TransactionObject)result.get());
			}
		}catch(IOException e) {
			e.printStackTrace();
		}

		return transactionList;
	}
}
