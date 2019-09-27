package jp.ethereum.transaction;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Numeric;

import jp.ethereum.common.ResultType;
import jp.ethereum.common.WebthereumCommon;
import jp.ethereum.contract.MyContractExec;

public class OriginalTokenTransaction {

    private static final String CONTRACT_ADDRESS = "0x88f19dbd33e4919a91f840506149eb54ddecf870";

    public static final String FUNC_BALANCE_OF = "balanceOf";

    public static final String FUNC_TRANSFER = "transfer";

    private static final MyContractExec exec = new MyContractExec();

    public BigInteger getBalance(String address) {

        List<?> result = null;

        try {
            List<Type> inputParam = new ArrayList<>();
            // 引数あり（address）／戻り値uint256
            inputParam.add(new Address(address));
            result = exec.execFunction(FUNC_BALANCE_OF, inputParam, ResultType.INT);

            return ((Uint)result.get(0)).getValue();
        }catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Type> sendSignedTransaction(Credentials credentials, List<Type> args, ResultType type) {

        Function function = new Function(
                FUNC_TRANSFER, args, Arrays.<TypeReference<?>>asList( getTypeReference(type) ));

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
            EthSendTransaction ethSendTransaction = WebthereumCommon.web3j.ethSendRawTransaction(hexValue).send();

            //エラー確認
            if(ethSendTransaction.getError() != null){
               System.out.println(ethSendTransaction.getError().getMessage());
            }else {
                String value = ethSendTransaction.getResult();
                return FunctionReturnDecoder.decode(value, function.getOutputParameters());
            }
        }catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private TypeReference<?> getTypeReference(ResultType type){

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
