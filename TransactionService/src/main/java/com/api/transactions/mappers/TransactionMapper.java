package com.api.transactions.mappers;

import com.api.transactions.model.Transaction;
import com.api.transactions.model.TransactionRequestBody;
import com.api.transactions.model.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {


    List<TransactionResponse> mapToTransactionResponseList(List<Transaction> source);


    Transaction mapToTransactionModel(TransactionRequestBody transactionRequestBody);
}
