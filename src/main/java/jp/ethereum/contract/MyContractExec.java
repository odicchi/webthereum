package jp.ethereum.contract;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Numeric;

import jp.ethereum.common.ResultType;
import jp.ethereum.common.WebthereumCommon;

public class MyContractExec {

    private static final String CONTRACT_ADDRESS = "0x88f19dbd33e4919a91f840506149eb54ddecf870";

    public static final String OWNER_ADDRESS = "0x4e90147dcdd6f6aed51714c9de4eae3899678c75";

    public static final String TO_ADDRESS = "0x9ee8bde2484bfa8c63fbd25e6059db0fd230a164";

    private static final MyContractExec exec = new MyContractExec();


    public static void main(String args[]) {


        List<?> result = null;
        try {
            List<Type> inputParam = new ArrayList<>();

            // 引数なし／戻り値uint256
            result = exec.execFunction("totalSupply", inputParam, ResultType.INT);
            System.out.println("Total Supply : " + ((Uint)result.get(0)).getValue());

            // FROM_ADRESS と TO_ADDRESSのBalance確認
            confirmBalance(OWNER_ADDRESS, TO_ADDRESS);


            // 引数あり（address, uint256）／戻り値bool
            inputParam = new ArrayList<>();
            inputParam.add(new Address(TO_ADDRESS));
            inputParam.add(new Uint(BigInteger.valueOf(123456)));
            result = exec.sendTransaction(WebthereumCommon.getCredentials(OWNER_ADDRESS), "transfer", inputParam, ResultType.BOOL);
            System.out.println( ((Bool)result.get(0)).getValue() );

            confirmBalance(OWNER_ADDRESS, TO_ADDRESS);


            result = sendSignedTransaction(WebthereumCommon.getCredentials(OWNER_ADDRESS), "transfer", inputParam, ResultType.BOOL);
            System.out.println( ((Bool)result.get(0)).getValue() );

            confirmBalance(OWNER_ADDRESS, TO_ADDRESS);

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void confirmBalance(String ... args) throws IOException{

        List<?> result = null;
        int i = 0;
        for(String address :args) {
            List<Type> inputParam = new ArrayList<>();
            // 引数あり（address）／戻り値uint256
            inputParam.add(new Address(address));
            result = exec.execFunction("balanceOf", inputParam, ResultType.INT);
            System.out.println("Balance of ADDRESS[" + i + "] : " +  ((Uint)result.get(0)).getValue() );
            i++;
        }
    }

    /**
     * Transactionの発生しない（値の更新がない）functionの実行
     * @param functionName
     * @return
     * @throws IOException
     */
    public List<?> execFunction(String functionName, List<Type> args, ResultType type) throws IOException{

        Function function = new Function(
                functionName, args, Arrays.<TypeReference<?>>asList( getTypeReference(type) ));

        String encodedFunction = FunctionEncoder.encode(function);
        EthCall ethCall = WebthereumCommon.web3j.ethCall(
                Transaction.createEthCallTransaction(
                        OWNER_ADDRESS, CONTRACT_ADDRESS, encodedFunction),
                DefaultBlockParameterName.LATEST)
                .send();

        String value = ethCall.getValue();
        return FunctionReturnDecoder.decode(value, function.getOutputParameters());
    }

    /**
     * Transactionが発生する（値の更新がある）functionの実行（署名なし）
     * @param credentials
     * @param functionName
     * @param args
     * @param type
     * @return
     */
    public List<?> sendTransaction(Credentials credentials, String functionName, List<Type> args, ResultType type){

        Function function = new Function(
                functionName, args, Arrays.<TypeReference<?>>asList( getTypeReference(type) ));

        String encodedFunction = FunctionEncoder.encode(function);

        try {
            // nonce値を取得する
            EthGetTransactionCount ethTransactionCount =
                WebthereumCommon.web3j.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.PENDING).send();
            BigInteger nonce = ethTransactionCount.getTransactionCount();

            // アカウントのアンロック
            PersonalUnlockAccount unlockAccountResponse = WebthereumCommon.web3j.personalUnlockAccount(OWNER_ADDRESS, "infomartpw").send();

            // アンロックが成功したらトランザクション送信
            if(unlockAccountResponse.getResult()) {

                EthSendTransaction sendTransaction = WebthereumCommon.web3j.ethSendTransaction(
                        Transaction.createFunctionCallTransaction(
                                credentials.getAddress(),
                                nonce,
                                BigInteger.valueOf(1000),
                                BigInteger.valueOf(4700000),
                                null,
                                encodedFunction)).send();

                // エラーが発生していたらコンソール出力
                if(sendTransaction.getError() != null) {
                    System.out.println(sendTransaction.getError().getMessage());
                }else {
                    String value = sendTransaction.getResult();
                    return FunctionReturnDecoder.decode(value, function.getOutputParameters());
                }
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<?> sendSignedTransaction(Credentials credentials, String functionName, List<Type> args, ResultType type){

        Function function = new Function(
                functionName, args, Arrays.<TypeReference<?>>asList( getTypeReference(type) ));

        String encodedFunction = FunctionEncoder.encode(function);

        try {
            // nonce値を取得する
            EthGetTransactionCount ethTransactionCount =
                WebthereumCommon.web3j.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.PENDING).send();
            BigInteger nonce = ethTransactionCount.getTransactionCount();

            //トランザクション生成
            RawTransaction rawTransaction = RawTransaction.createTransaction(
                    nonce,
                    BigInteger.valueOf(1000),
                    BigInteger.valueOf(4700000),
                    CONTRACT_ADDRESS,
                    encodedFunction);

            //署名
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);

            //send
            EthSendTransaction ethSendTransaction = WebthereumCommon.web3j.ethSendRawTransaction(hexValue).sendAsync().get();

            //エラー確認
            if(ethSendTransaction.getError() != null){
               System.out.println(ethSendTransaction.getError().getMessage());
            }else {
                String value = ethSendTransaction.getResult();
                return FunctionReturnDecoder.decode(value, function.getOutputParameters());
            }
        }catch(IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static TypeReference<?> getTypeReference(ResultType type){

        TypeReference<?> typeReference = null;
        switch(type) {
        case ADDRESS:
            typeReference = new TypeReference<Address>() {};
            break;
        case BOOL:
            typeReference = new TypeReference<Bool>() {};
            break;
        case STRING:
            typeReference = new TypeReference<Utf8String>() {};
            break;
        case INT:
            typeReference = new TypeReference<Uint>() {};
            break;
        default:
            break;
        }
        return typeReference;
    }
}
