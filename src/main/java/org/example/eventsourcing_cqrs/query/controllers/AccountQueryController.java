package org.example.eventsourcing_cqrs.query.controllers;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.example.eventsourcing_cqrs.dtos.AccountStatementDTO;
import org.example.eventsourcing_cqrs.query.entities.Account;
import org.example.eventsourcing_cqrs.query.entities.Operation;
import org.example.eventsourcing_cqrs.query.queries.AccountStatementQuery;
import org.example.eventsourcing_cqrs.query.queries.GetAllAccountsQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/query/accounts")
public class AccountQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("")
    public CompletableFuture<List<Account>> getAllAccounts(){
        CompletableFuture<List<Account>> response = queryGateway.query(new GetAllAccountsQuery(), ResponseTypes.multipleInstancesOf(Account.class));
        return response;

    }


    @GetMapping("/accountStatement/{accountId}")
    public CompletableFuture<AccountStatementDTO> getAccountStatement(@PathVariable String accountId){

        CompletableFuture<AccountStatementDTO> response  = queryGateway.query(new AccountStatementQuery(accountId), ResponseTypes.instanceOf(AccountStatementDTO.class));
        return response;

    }



    @GetMapping(value = "/watch/{accountId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)//: indique que la réponse sera un flux d'événements envoyé progressivement au client (SSE).
    public Flux<Operation> watch(@PathVariable String accountId){
        SubscriptionQueryResult<Operation, Operation> result = queryGateway.subscriptionQuery(new WatchEventQuery(accountId),
                ResponseTypes.instanceOf(Operation.class),
                ResponseTypes.instanceOf(Operation.class)
        );
        return result.initialResult().concatWith(result.updates());
    }

}
