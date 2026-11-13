package org.example.eventsourcing_cqrs.query.handlers;

import org.axonframework.queryhandling.QueryHandler;
import org.example.eventsourcing_cqrs.dtos.AccountStatementDTO;
import org.example.eventsourcing_cqrs.query.controllers.WatchEventQuery;
import org.example.eventsourcing_cqrs.query.queries.AccountStatementQuery;
import org.example.eventsourcing_cqrs.query.entities.Account;
import org.example.eventsourcing_cqrs.query.entities.Operation;
import org.example.eventsourcing_cqrs.query.queries.GetAllAccountsQuery;
import org.example.eventsourcing_cqrs.query.repositories.AccountRepository;
import org.example.eventsourcing_cqrs.query.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountQueryHandler {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private OperationRepository operationRepository;

    @QueryHandler
    public List<Account> on(GetAllAccountsQuery getAllAccountsQuery){
        return accountRepository.findAll();
    }

    @QueryHandler
    public AccountStatementDTO on(AccountStatementQuery accountStatementQuery){
        Account account = accountRepository.findById(accountStatementQuery.getAccountId()).get();
        List<Operation> operations = operationRepository.findByAccountId(accountStatementQuery.getAccountId());
        AccountStatementDTO accountStatementDTO = new AccountStatementDTO(account,operations);
        return accountStatementDTO;
    }

    @QueryHandler
    public Operation on(WatchEventQuery watchEventQuery){
        return new Operation();
    }


}
