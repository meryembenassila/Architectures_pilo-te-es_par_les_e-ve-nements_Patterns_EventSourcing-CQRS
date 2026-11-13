package org.example.eventsourcing_cqrs.query.handlers;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.example.eventsourcing_cqrs.events.*;
import org.example.eventsourcing_cqrs.query.entities.Account;
import org.example.eventsourcing_cqrs.query.entities.Operation;
import org.example.eventsourcing_cqrs.query.entities.OperationType;
import org.example.eventsourcing_cqrs.query.repositories.AccountRepository;
import org.example.eventsourcing_cqrs.query.repositories.OperationRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AccountEventHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    private QueryUpdateEmitter queryUpdateEmitter;


    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent, EventMessage eventMessage){
        log.info("==================Query Side : AccountCreatedEvent Received");
        Account account = new Account(accountCreatedEvent.getId(),
                eventMessage.getTimestamp(),
                accountCreatedEvent.getInitialBalance(),
                accountCreatedEvent.getAccountStatus(),
                accountCreatedEvent.getCurrency()

               );
        accountRepository.save(account);

    }

    @EventHandler
    public void on(AccountCreditedEvent accountCreditedEvent,EventMessage eventMessage){
        log.info("==================Query Side : AccountCreditedEvent Received");
        Account account = accountRepository.findById(accountCreditedEvent.getId()).get();
        Operation operation = new Operation();
        operation.setType(OperationType.CREDIT);
        operation.setAmount(accountCreditedEvent.getAmount());
        operation.setDate(eventMessage.getTimestamp());
        operation.setCurrency(accountCreditedEvent.getCurrency());
        operation.setAccount(account);
        operationRepository.save(operation);

        account.setBalance(account.getBalance()+accountCreditedEvent.getAmount());
        accountRepository.save(account);

        //pour flux des operations
        queryUpdateEmitter.emit(e->true,operation);

    }


    @EventHandler
    public void on(AccountDebitedEvent accountDebitedEvent,EventMessage eventMessage){
        log.info("==================Query Side : AccountDebitedEvent Received");
        Account account = accountRepository.findById(accountDebitedEvent.getId()).get();
        Operation operation = new Operation();
        operation.setType(OperationType.DEBIT);
        operation.setAmount(accountDebitedEvent.getAmount());
        operation.setDate(eventMessage.getTimestamp());
        operation.setCurrency(accountDebitedEvent.getCurrency());
        operation.setAccount(account);
        operationRepository.save(operation);


        account.setBalance(account.getBalance() - accountDebitedEvent.getAmount());
        accountRepository.save(account);

        queryUpdateEmitter.emit(e->true,operation);

    }

    @EventHandler
    public void on(AccountActivatedEvent accountActivatedEvent){
        log.info("==================Query Side : AccountActivatedEvent Received");
        Account account = accountRepository.findById(accountActivatedEvent.getAccountId()).orElseThrow(() -> new IllegalStateException(
                "Account not found: " + accountActivatedEvent.getAccountId()));
        account.setStatus(accountActivatedEvent.getAccountStatus());
        accountRepository.save(account);
    }


    @EventHandler
    public void on(AccountChangedStatusEvent accountChangedStatusEvent){
        Account account = accountRepository.findById(accountChangedStatusEvent.getAccountId()).get();
        account.setStatus(accountChangedStatusEvent.getAccountStatus());
        accountRepository.save(account);
    }



}
