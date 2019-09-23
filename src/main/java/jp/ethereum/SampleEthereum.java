package jp.ethereum;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.response.NoOpProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

import jp.ethereum.contract.Addition;

public class SampleEthereum {

	private static final String BINARY = "0x608060405234801561001057600080fd5b506040516020806106d0833981016040525160008054600160a060020a03909216600160a060020a031990921691909117905561067e806100526000396000f30060806040526004361061006c5763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663246982c4811461007157806362d8e68e1461010857806367b0dbf91461016557806371cf56d314610186578063877dd5be146101ad575b600080fd5b34801561007d57600080fd5b506100896004356101c2565b6040518080602001838152602001828103825284818151815260200191508051906020019080838360005b838110156100cc5781810151838201526020016100b4565b50505050905090810190601f1680156100f95780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b34801561011457600080fd5b506040805160206004803580820135601f810184900484028501840190955284845261016394369492936024939284019190819084018382808284375094975050933594506103519350505050565b005b34801561017157600080fd5b50610163600160a060020a0360043516610440565b34801561019257600080fd5b5061019b61046f565b60408051918252519081900360200190f35b3480156101b957600080fd5b5061019b6105b4565b60008054604080517f40ab422e00000000000000000000000000000000000000000000000000000000815260048101859052905160609392600160a060020a0316916340ab422e916024808301928692919082900301818387803b15801561022957600080fd5b505af115801561023d573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561026657600080fd5b81019080805164010000000081111561027e57600080fd5b8201602081018481111561029157600080fd5b81516401000000008111828201871017156102ab57600080fd5b505060008054604080517f689b55dd000000000000000000000000000000000000000000000000000000008152600481018b90529051939650600160a060020a03909116945063689b55dd9350602480820193602093909283900390910190829087803b15801561031b57600080fd5b505af115801561032f573d6000803e3d6000fd5b505050506040513d602081101561034557600080fd5b50519092509050915091565b60008054604080517f2fda437e0000000000000000000000000000000000000000000000000000000081526024810185905260048101918252855160448201528551600160a060020a0390931693632fda437e93879387939092839260649092019160208701918190849084905b838110156103d75781810151838201526020016103bf565b50505050905090810190601f1680156104045780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561042457600080fd5b505af1158015610438573d6000803e3d6000fd5b505050505050565b6000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b60008060008061047d6105b4565b9250600083116104ee57604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601360248201527f556e7265676973746572656420506572736f6e00000000000000000000000000604482015290519081900360640190fd5b5060009050805b828110156105a05760008054604080517f689b55dd0000000000000000000000000000000000000000000000000000000081526001850160048201529051600160a060020a039092169263689b55dd926024808401936020939083900390910190829087803b15801561056757600080fd5b505af115801561057b573d6000803e3d6000fd5b505050506040513d602081101561059157600080fd5b505191909101906001016104f5565b82828115156105ab57fe5b04935050505090565b60008060009054906101000a9004600160a060020a0316600160a060020a031663d0afcb326040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561062157600080fd5b505af1158015610635573d6000803e3d6000fd5b505050506040513d602081101561064b57600080fd5b50519050905600a165627a7a723058201e93ebbc11a4fbcc5285c896468ccafa2ba48c696335d096be64138ac7d582010029";

	private static final String ADDRESS = "0x945Cd603A6754cB13C3D61d8fe240990f86f9f8A";

	// Ethereumクライアントと接続
	private static final Web3j web3j = Web3j.build(new HttpService("http://localhost:8545"));

	public static void main(String[] args) {

//		connectEthereum();
//		ethGetBlockByNumber();
//		ethSendTransaction();

		ethSampleContract2();
	}

	public static void connectEthereum() {

		System.out.println("connectEthereum start");

		System.out.println("Connecting to Ethereum ...");
	    Web3j web3 = Web3j.build(new HttpService("http://localhost:8545"));
	    System.out.println("Successfuly connected to Ethereum");

	    try {
	      // web3_clientVersion returns the current client version.
	      Web3ClientVersion clientVersion = web3.web3ClientVersion().send();

	      // eth_blockNumber returns the number of most recent block.
	      EthBlockNumber blockNumber = web3.ethBlockNumber().send();

	      // eth_gasPrice, returns the current price per gas in wei.
	      EthGasPrice gasPrice = web3.ethGasPrice().send();

	      // Print result
	      System.out.println("Client version: " + clientVersion.getWeb3ClientVersion());
	      System.out.println("Block number: " + blockNumber.getBlockNumber());
	      System.out.println("Gas price: " + gasPrice.getGasPrice());

	    } catch (IOException ex) {
	      throw new RuntimeException("Error whilst sending json-rpc requests", ex);
	    }

		System.out.println("connectEthereum end");
	}

	public static void ethGetBlockByNumber() {

		System.out.println("ethGetBlockByNumber start");

		try {
			// JSON-RPCのリクエストを送信し、レスポンスを受信する
			EthBlock response = web3j.ethGetBlockByNumber(
			    DefaultBlockParameterName.LATEST, // block number。ここでは最新のブロックを表すタグである"latest"を指定する
			    false // trueの場合、トランザクションの詳細を取得する。falseの場合、トランザクションハッシュのみ取得する
			    ).send();
			// 結果を出力
			Block block = response.getResult();
			System.out.println("hash:" + block.getHash());
			System.out.println("number:" + block.getNumber());
		} catch (IOException ex) {
			throw new RuntimeException("Error whilst sending json-rpc requests", ex);
		}

		System.out.println("ethGetBlockByNumber end");
	}

	public static void ethSendTransaction() {

		System.out.println("ethSendTransaction start");
		// Ethereumクライアントと接続
		// AdminはWeb3jのサブクラスで、"personal_unlockAccount"のリクエスト処理が実装されています。
		Admin web3j = Admin.build(new HttpService("http://localhost:8545"));

		try {
			// "personal_unlockAccount"のリクエストを送信し、レスポンスを受信する
			PersonalUnlockAccount unlockAccountResponse = web3j.personalUnlockAccount(
			    "0x945cd603a6754cb13c3d61d8fe240990f86f9f8a", // アドレス
			    "blah" // パスワード
			    ).send();

			// アンロックが成功していたら、Etherを送金する
			if (unlockAccountResponse.getResult()) {
			    // "eth_sendTransaction"の引数に渡すオブジェクトを作成
			    Transaction transaction = Transaction.createEtherTransaction(
			        "0x945cd603a6754cb13c3d61d8fe240990f86f9f8a", // from
			        null, // nonce。今回は指定しないため、null
			        null, // gasPrice。今回は指定しないため、null
			        null, // gasLimit。今回は指定しないため、null
			        "0x66b4e7be902300f9a15d900822bbd8803be87391", // to
			        BigInteger.valueOf(100) // value
			        );

			    // "eth_sendTransaction"のリクエストを送信し、レスポンスを受信する
			    EthSendTransaction sendTransactionResponse = web3j.ethSendTransaction(transaction).send();
			    // 結果を出力
			    System.out.println(sendTransactionResponse.getResult());
			}
		} catch(IOException ex) {
			throw new RuntimeException("Error whilst sending json-rpc requests", ex);
		}
		System.out.println("ethSendTransaction end");
	}

	public static void ethSampleContract() {

		System.out.println("ethSampleContract start");
		try {
			List inputParams = new ArrayList();
			List outputParams = new ArrayList();
			Function function = new Function("getPersonCntCall", inputParams, outputParams);
			String encodedFunction = FunctionEncoder.encode(function);

			BigInteger nonce = BigInteger.valueOf(5);
			BigInteger gasprice = BigInteger.valueOf(100);
			BigInteger gaslimit = BigInteger.valueOf(4712388);

			Transaction transaction = Transaction
			  .createFunctionCallTransaction("0x66b4e7be902300f9a15d900822bbd8803be87391",
			    nonce, gasprice, gaslimit, "0x945cd603a6754cb13c3d61d8fe240990f86f9f8a", encodedFunction);

			EthSendTransaction transactionResponse = web3j.ethSendTransaction(transaction).sendAsync().get();
			String transactionHash = transactionResponse.getTransactionHash();
			System.out.println(transactionResponse.getResult());
			System.out.println(transactionHash);
		} catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println("ethSampleContract end");
	}

	public static void ethSampleContract2() {

		String toAccount = "0x945cd603a6754cb13c3d61d8fe240990f86f9f8a";

		int coinAmount = 1; // e.g 1 dog coin

		// if testing, use https://ropsten.etherscan.io/address/[Your contract address]
		Web3j web3 = Web3j.build(new HttpService("http://localhost:8545"));
		try{

			Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
			String clientVersion = web3ClientVersion.getWeb3ClientVersion();
			System.out.println("clientVersion " + clientVersion);

			String privateKey = "d998aa921a4d768240c071122a5e9f77bd15c759a76e588dd19892e5702ad281";
			BigInteger key = new BigInteger(privateKey,16);
			ECKeyPair ecKeyPair = ECKeyPair.create(key.toByteArray());

			Credentials credentials;
			try {
				//Credentials credentials = Credentials.create(ecKeyPair);
				credentials = WalletUtils.loadCredentials("blah", "C:\\Users\\crypr\\Documents\\private-net\\keystore\\UTC--2017-12-13T18-58-05.147750439Z--945cd603a6754cb13c3d61d8fe240990f86f9f8a");

				TransactionReceiptProcessor transactionReceiptProcessor = new NoOpProcessor(web3);
				TransactionManager transactionManager = new RawTransactionManager(
						web3, credentials, 1, transactionReceiptProcessor);
				//if testing, use ChainId.ROPSTEN

				// need to use the java wrapper filed generated before
				Addition mycontract = Addition.load("0x90bde7d0d38510f5c7543f85c7ad18f1fb1bf5d1", web3, transactionManager, new SampleGasProvider(new BigInteger("1", 10), new BigInteger("100000", 10)));

				//BigInteger _value = BigInteger.valueOf((long) (coinAmount *Math.pow(10, 8)));

				Admin admin = Admin.build(new HttpService("http://localhost:8545"));


				RemoteFunctionCall<BigInteger> call = mycontract.get();
				BigInteger getResult = call.send();
				System.out.println("get:" + getResult);

				// "personal_unlockAccount"のリクエストを送信し、レスポンスを受信する
				PersonalUnlockAccount unlockAccountResponse = admin.personalUnlockAccount(
				    "0x945cd603a6754cb13c3d61d8fe240990f86f9f8a", // アドレス
				    "blah" // パスワード
				    ).send();


				// アンロックが成功していたら、Etherを送金する
				if (unlockAccountResponse.getResult()) {
				    // "eth_sendTransaction"の引数に渡すオブジェクトを作成
				    Transaction transaction = Transaction.createEtherTransaction(
				        "0x945cd603a6754cb13c3d61d8fe240990f86f9f8a", // from
				        null, // nonce。今回は指定しないため、null
				        null, // gasPrice。今回は指定しないため、null
				        null, // gasLimit。今回は指定しないため、null
				        "0x66b4e7be902300f9a15d900822bbd8803be87391", // to
				        BigInteger.valueOf(100) // value
				        );

				    // "eth_sendTransaction"のリクエストを送信し、レスポンスを受信する
				    EthSendTransaction sendTransactionResponse = admin.ethSendTransaction(transaction).send();
				    // 結果を出力
				    System.out.println(sendTransactionResponse.getResult());
				}

				// コントラクトをデプロイ

				TransactionReceipt addResult = mycontract.add(new BigInteger("1", 10)).send();
				System.out.println("add:" + addResult);
				getResult = call.send();
				System.out.println("get:" + getResult);

				String sTransHash = addResult.getTransactionHash();


				System.out.println("toAccount: " + toAccount + " coinAmount: " + coinAmount + " transactionhash: " + sTransHash);

				// You can view the transaction record on https://etherscan.io/tx/[transaction hash]
				// if testing , on https://ropsten.etherscan.io/tx/[transaction hash]
			} catch (Exception e) {
				e.printStackTrace();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
