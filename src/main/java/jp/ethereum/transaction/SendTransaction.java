package jp.ethereum.transaction;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert.Unit;
import org.web3j.utils.Numeric;

import jp.ethereum.common.WebthereumCommon;

public class SendTransaction {

  // ①
  public static final Admin web3j = Admin.build(new HttpService("http://192.168.33.11:30302"));

  public static void main(String args[]) {

    try {

      // ②
      // トランザクション送信者の資格情報を取得する
      // 資格情報に公開鍵、秘密鍵が含まれている
      String password = "blah";
      Credentials credentials = WalletUtils.loadCredentials(password, "C:\\Users\\crypr\\Documents\\private-net\\keystore\\UTC--2017-12-13T18-58-05.147750439Z--945cd603a6754cb13c3d61d8fe240990f86f9f8a");

      String toAddress = "0x66b4e7be902300f9a15d900822bbd8803be87391";

      SendTransaction tx= new SendTransaction();

      // トランザクションの送信
      // tx.sendTransaction(credentials, password, toAddress, 10);

      // トランザクションの送信（Async）
      //TransactionReceipt receipt = tx.sendTransactionAsync(credentials, password, toAddress, 10);
      // 署名付きトランザクションの送信
      TransactionReceipt receipt = tx.sendSignedTransaction(credentials, toAddress, BigInteger.valueOf(1000));

      if (receipt != null) System.out.println(receipt.getTransactionHash()) ;

    }catch(IOException | CipherException ex) {
      ex.printStackTrace();
    }
  }

  private void minerStart() {
	  web3j.ethMining();
  }

  public TransactionReceipt sendTransaction(Credentials credentials, String password, String toAddress, long value) {

    TransactionReceipt receipt = null;
      try {
        // トランザクションの生成
        // "personal_unlockAccount"のリクエストを送信し、レスポンスを受信する
        PersonalUnlockAccount unlockAccountResponse = web3j.personalUnlockAccount(
            credentials.getAddress(), // アドレス
            password  // パスワード
            ).send();

        // アンロックが成功していたら、Etherを送金する
        if (unlockAccountResponse.getResult()) {
          // Transactionを送信する。Blockに取り込まれるまで応答が返ってこない
          receipt = Transfer.sendFunds(web3j, credentials, toAddress, BigDecimal.valueOf(value), Unit.ETHER).send();
        }
      }catch(IOException | TransactionException ex) {
        ex.printStackTrace();
      }catch(Exception ex) {
        ex.printStackTrace();
      }
    return receipt;
  }

  public TransactionReceipt sendTransactionAsync(Credentials credentials, String password, String toAddress, long value) {

    CompletableFuture<TransactionReceipt> receipt = null;
    try {
      // トランザクションの生成
      // "personal_unlockAccount"のリクエストを送信し、レスポンスを受信する
      PersonalUnlockAccount unlockAccountResponse = web3j.personalUnlockAccount(
            credentials.getAddress(), // アドレス
            password  // パスワード
            ).send();

      // アンロックが成功していたら、Etherを送金する
      if (unlockAccountResponse.getResult()) {
        // Transactionを送信する。
        receipt = Transfer.sendFunds(web3j, credentials, toAddress, BigDecimal.valueOf(value), Unit.ETHER).sendAsync();
      }

      return receipt.get();
    }catch(IOException | TransactionException ex) {
      ex.printStackTrace();
    }catch(Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public TransactionReceipt sendSignedTransaction(Credentials credentials, String toAddress, BigInteger value) {

      TransactionReceipt receipt = null;
      try {
        // nonce値を取得する
        EthGetTransactionCount ethTransactionCount =
            WebthereumCommon.web3j.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.PENDING).send();
        BigInteger nonce = ethTransactionCount.getTransactionCount();

        // "eth_sendTransaction"の引数に渡すオブジェクトを作成
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
            nonce,                        // nonce
            BigInteger.valueOf(100),     // gasPrice
            BigInteger.valueOf(4712388), // gasLimit
            toAddress,                    // to
            value                         // value
            );

        // トランザクションに署名
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
        String transactionHash = ethSendTransaction.getTransactionHash();

        // トランザクションの送信
        Optional<TransactionReceipt> transactionReceipt = null;
        int retry = 0;

        if(transactionHash != null) {
          do {
            System.out.printf("%3d checking if transaction " + transactionHash + " is mined....\n" ,retry);
            EthGetTransactionReceipt ethGetTransactionReceiptResp = web3j.ethGetTransactionReceipt(transactionHash).send();
            transactionReceipt = ethGetTransactionReceiptResp.getTransactionReceipt();

            Thread.sleep(3000);
            retry++;
          }while(!transactionReceipt.isPresent() && retry < 100);
        } else {
          System.out.println("Transaction Send failed...");
          System.out.println("Message:" + ethSendTransaction.getError().getMessage());
          System.out.println("Data   :" + ethSendTransaction.getError().getData());
        }
      }catch(IOException | InterruptedException ex) {
        ex.printStackTrace();
      }catch(Exception ex) {
        ex.printStackTrace();
      }
    return receipt;
  }
}


