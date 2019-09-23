package jp.ethereum.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.0.
 */
public class PersonContract extends Contract {
    private static final String BINARY = "6080604052600060015534801561001557600080fd5b506104f3806100256000396000f300608060405260043610610062576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680632fda437e1461006757806340ab422e146100da578063689b55dd14610180578063d0afcb32146101c1575b600080fd5b34801561007357600080fd5b506100d8600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291905050506101ec565b005b3480156100e657600080fd5b506101056004803603810190808035906020019092919050505061024a565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561014557808201518184015260208101905061012a565b50505050905090810190601f1680156101725780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561018c57600080fd5b506101ab6004803603810190808035906020019092919050505061037d565b6040518082815260200191505060405180910390f35b3480156101cd57600080fd5b506101d6610418565b6040518082815260200191505060405180910390f35b6001600081548092919060010191905055508160008060015481526020019081526020016000206000019080519060200190610229929190610422565b50806000806001548152602001908152602001600020600101819055505050565b60608160015481111515156102c7576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260138152602001807f556e72656769737465726564204e756d6265720000000000000000000000000081525060200191505060405180910390fd5b6000808481526020019081526020016000206000018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103705780601f1061034557610100808354040283529160200191610370565b820191906000526020600020905b81548152906001019060200180831161035357829003601f168201915b5050505050915050919050565b60008160015481111515156103fa576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260138152602001807f556e72656769737465726564204e756d6265720000000000000000000000000081525060200191505060405180910390fd5b60008084815260200190815260200160002060010154915050919050565b6000600154905090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061046357805160ff1916838001178555610491565b82800160010185558215610491579182015b82811115610490578251825591602001919060010190610475565b5b50905061049e91906104a2565b5090565b6104c491905b808211156104c05760008160009055506001016104a8565b5090565b905600a165627a7a72305820dabe9764d04478b09af7ba902ae32cc179cdb8a4922828387244a4e819b4edc00029\r\n";

    public static final String FUNC_SETPERSON = "setPerson";

    public static final String FUNC_GETPERSONNAME = "getPersonName";

    public static final String FUNC_GETPERSONAGE = "getPersonAge";

    public static final String FUNC_GETPERSONCNT = "getPersonCnt";

    @Deprecated
    protected PersonContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PersonContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PersonContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PersonContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> setPerson(String _name, BigInteger _age) {
        final Function function = new Function(
                FUNC_SETPERSON, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.generated.Uint256(_age)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> getPersonName(BigInteger _no) {
        final Function function = new Function(FUNC_GETPERSONNAME, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_no)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> getPersonAge(BigInteger _no) {
        final Function function = new Function(FUNC_GETPERSONAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_no)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getPersonCnt() {
        final Function function = new Function(FUNC_GETPERSONCNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static PersonContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PersonContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PersonContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PersonContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PersonContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PersonContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PersonContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PersonContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PersonContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PersonContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PersonContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PersonContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<PersonContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PersonContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PersonContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PersonContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
