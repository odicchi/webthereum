package jp.ethereum.bean;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;

import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionObject;

import jp.ethereum.common.WebthereumCommon;

@Named(value = "explorerBean")
@ManagedBean
@RequestScoped
public class ExplorerBean {

	List<EthBlock> blockList;
	EthBlock ethBlock;

	String blockNumber;

	List<TransactionObject> transactionList;

	@PostConstruct
	public void init() {
		blockList = WebthereumCommon.getBlock();
	}

	public List<EthBlock> getBlockList() {
		return blockList;
	}

	public EthBlock getEthBlock() {
		return ethBlock;
	}

	public void setEthBlock(EthBlock ethBlock) {
		this.ethBlock = ethBlock;
	}

	public List<TransactionObject> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<TransactionObject> transactionList) {
		this.transactionList = transactionList;
	}

	public String getBlockNumber() {
		return blockNumber;
	}

	public void setBlockNumber(String blockNumber) {
		this.blockNumber = blockNumber;
	}

	public void setBlockList(List<EthBlock> blockList) {
		this.blockList = blockList;
	}

	public String detail(String blockNumber) {

		BigInteger bn = BigInteger.valueOf(Long.valueOf(blockNumber));
		transactionList = WebthereumCommon.getTransactionList(bn);
		ethBlock = WebthereumCommon.getBlock(bn);
		return "detail.xhtml";
	}

}
