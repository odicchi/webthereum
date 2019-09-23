package jp.ethereum;

import java.math.BigInteger;

import org.web3j.tx.gas.ContractGasProvider;

public class SampleGasProvider implements ContractGasProvider{

	private BigInteger gasPrice;

	private BigInteger gasLimit;

	public SampleGasProvider(BigInteger gasPrice, BigInteger gasLimit) {
		this.gasPrice = gasPrice;
		this.gasLimit = gasLimit;
	}

	@Override
	public BigInteger getGasPrice(String contractFunc) {
		return this.gasPrice;
	}

	@Override
	public BigInteger getGasPrice() {
		return this.gasPrice;
	}

	@Override
	public BigInteger getGasLimit(String contractFunc) {
		return this.gasLimit;
	}

	@Override
	public BigInteger getGasLimit() {
		return this.gasLimit;
	}


}
