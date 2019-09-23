package jp.ethereum.sample;

import java.math.BigInteger;
import java.util.List;

import org.web3j.protocol.core.methods.response.EthBlock;

import jp.ethereum.common.WebthereumCommon;

public class Sample {

	public static void main(String args[]) {

		List<EthBlock> blockList = WebthereumCommon.getBlock();

		for(EthBlock block: blockList) {
			System.out.println("----------------------------------------------------------------");
			System.out.println("ID         : " + block.getId());
			System.out.println("Author     : " + block.getResult().getAuthor());
			System.out.println("Difficulty : " + block.getResult().getDifficulty());
			System.out.println("ExtraData  : " + block.getResult().getExtraData());
			System.out.println("GasLimit   : " + block.getResult().getGasLimit());
			System.out.println("GasUsed    : " + block.getResult().getGasUsed());
			System.out.println("Nonce      : " + block.getResult().getNonce());
			System.out.println("Size       : " + block.getResult().getSize());
			System.out.println("Number     : " + block.getResult().getNumber());
			System.out.println("Size       : " + block.getResult().getSize());
			System.out.println("Transactions: "+ block.getResult().getTransactions().size());

			System.out.println("Hash       : " + block.getResult().getHash());
			System.out.println("Author     : " + block.getResult().getAuthor());
			System.out.println("Difficulty : " + block.getResult().getDifficultyRaw());
			System.out.println("ExtraData  : " + block.getResult().getExtraData());
			System.out.println("GasLimit   : " + block.getResult().getGasLimitRaw());
			System.out.println("GasUsed    : " + block.getResult().getGasUsedRaw());
			System.out.println("Nonce      : " + block.getResult().getNonceRaw());
			System.out.println("Size       : " + block.getResult().getSizeRaw());
			System.out.println("Number     : " + block.getResult().getNumberRaw());
			System.out.println("Size       : " + block.getResult().getSizeRaw());

		}

		EthBlock block = WebthereumCommon.getBlock(BigInteger.valueOf(2846));

		System.out.println("----------------------------------------------------------------");
		System.out.println("ID         : " + block.getId());
		System.out.println("Author     : " + block.getResult().getAuthor());
		System.out.println("Difficulty : " + block.getResult().getDifficulty());
		System.out.println("ExtraData  : " + block.getResult().getExtraData());
		System.out.println("GasLimit   : " + block.getResult().getGasLimit());
		System.out.println("GasUsed    : " + block.getResult().getGasUsed());
		System.out.println("Nonce      : " + block.getResult().getNonce());
		System.out.println("Size       : " + block.getResult().getSize());
		System.out.println("Number     : " + block.getResult().getNumber());
		System.out.println("Size       : " + block.getResult().getSize());
		System.out.println("Transactions: "+ block.getResult().getTransactions().size());

		System.out.println("Hash       : " + block.getResult().getHash());
		System.out.println("Author     : " + block.getResult().getAuthor());
		System.out.println("Difficulty : " + block.getResult().getDifficultyRaw());
		System.out.println("ExtraData  : " + block.getResult().getExtraData());
		System.out.println("GasLimit   : " + block.getResult().getGasLimitRaw());
		System.out.println("GasUsed    : " + block.getResult().getGasUsedRaw());
		System.out.println("Nonce      : " + block.getResult().getNonceRaw());
		System.out.println("Size       : " + block.getResult().getSizeRaw());
		System.out.println("Number     : " + block.getResult().getNumberRaw());
		System.out.println("Size       : " + block.getResult().getSizeRaw());

	}
}
