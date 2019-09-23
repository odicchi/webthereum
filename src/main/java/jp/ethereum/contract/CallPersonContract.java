package jp.ethereum.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
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
import org.web3j.tuples.generated.Tuple2;
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
public class CallPersonContract extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060405160208061088283398101806040528101908080519060200190929190505050806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550506107ff806100836000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063246982c41461007257806362d8e68e1461011f57806367b0dbf91461019257806371cf56d3146101d5578063877dd5be14610200575b600080fd5b34801561007e57600080fd5b5061009d6004803603810190808035906020019092919050505061022b565b6040518080602001838152602001828103825284818151815260200191508051906020019080838360005b838110156100e35780820151818401526020810190506100c8565b50505050905090810190601f1680156101105780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b34801561012b57600080fd5b50610190600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610424565b005b34801561019e57600080fd5b506101d3600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061053d565b005b3480156101e157600080fd5b506101ea610580565b6040518082815260200191505060405180910390f35b34801561020c57600080fd5b5061021561070c565b6040518082815260200191505060405180910390f35b606060008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166340ab422e846040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180828152602001915050600060405180830381600087803b1580156102bf57600080fd5b505af11580156102d3573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f8201168201806040525060208110156102fd57600080fd5b81019080805164010000000081111561031557600080fd5b8281019050602081018481111561032b57600080fd5b815185600182028301116401000000008211171561034857600080fd5b50509291905050506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663689b55dd856040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180828152602001915050602060405180830381600087803b1580156103e057600080fd5b505af11580156103f4573d6000803e3d6000fd5b505050506040513d602081101561040a57600080fd5b810190808051906020019092919050505091509150915091565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16632fda437e83836040518363ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001838152602001828103825284818151815260200191508051906020019080838360005b838110156104d45780820151818401526020810190506104b9565b50505050905090810190601f1680156105015780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561052157600080fd5b505af1158015610535573d6000803e3d6000fd5b505050505050565b806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b60008060008061058e61070c565b9250600083111515610608576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260138152602001807f556e7265676973746572656420506572736f6e0000000000000000000000000081525060200191505060405180910390fd5b60009150600090505b828110156106f8576000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663689b55dd600183016040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180828152602001915050602060405180830381600087803b1580156106ac57600080fd5b505af11580156106c0573d6000803e3d6000fd5b505050506040513d60208110156106d657600080fd5b8101908080519060200190929190505050820191508080600101915050610611565b828281151561070357fe5b04935050505090565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663d0afcb326040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561079357600080fd5b505af11580156107a7573d6000803e3d6000fd5b505050506040513d60208110156107bd57600080fd5b81019080805190602001909291905050509050905600a165627a7a7230582030abfee2c1a619fd885b6aaa71c2a6691bc90db2f9facaffafd67ae60c1a084a0029";

    public static final String FUNC_GETPERSON = "getPerson";

    public static final String FUNC_SETPERSONCALL = "setPersonCall";

    public static final String FUNC_SETPERSONCONTADDR = "setPersonContAddr";

    public static final String FUNC_GETPERSONAGEAVERAGE = "getPersonAgeAverage";

    public static final String FUNC_GETPERSONCNTCALL = "getPersonCntCall";

    @Deprecated
    protected CallPersonContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CallPersonContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CallPersonContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CallPersonContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<Tuple2<String, BigInteger>> getPerson(BigInteger _no) {
        final Function function = new Function(FUNC_GETPERSON, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_no)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple2<String, BigInteger>>(function,
                new Callable<Tuple2<String, BigInteger>>() {
                    @Override
                    public Tuple2<String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> setPersonCall(String _name, BigInteger _age) {
        final Function function = new Function(
                FUNC_SETPERSONCALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.generated.Uint256(_age)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPersonContAddr(String _address) {
        final Function function = new Function(
                FUNC_SETPERSONCONTADDR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getPersonAgeAverage() {
        final Function function = new Function(FUNC_GETPERSONAGEAVERAGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getPersonCntCall() {
        final Function function = new Function(FUNC_GETPERSONCNTCALL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static CallPersonContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CallPersonContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CallPersonContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CallPersonContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CallPersonContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CallPersonContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CallPersonContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CallPersonContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CallPersonContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _address) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)));
        return deployRemoteCall(CallPersonContract.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<CallPersonContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _address) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)));
        return deployRemoteCall(CallPersonContract.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<CallPersonContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _address) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)));
        return deployRemoteCall(CallPersonContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<CallPersonContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _address) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)));
        return deployRemoteCall(CallPersonContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
