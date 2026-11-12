package org.example.eventsourcing_cqrs.aggregate;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.eventsourcing_cqrs.command.commands.AddAccountCommand;
import org.example.eventsourcing_cqrs.enums.AccountStatus;
import org.example.eventsourcing_cqrs.events.AccountCreatedEvent;

@Aggregate
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance ;
    private AccountStatus accountStatus;
    //pour Axon
    public AccountAggregate(){};


    @CommandHandler //fonction de decision
    public AccountAggregate(AddAccountCommand command){
        System.out.println("COMMAND HANDLER EXECUTED");
        log.info("############# CommandHandler : AddAccountCommand created ########################## ");
      if(command.getInitialBalance()<=0) throw  new IllegalArgumentException("Balance must be positive");
      AggregateLifecycle.apply(new AccountCreatedEvent(command.getId(),command.getInitialBalance(),command.getCurrency(),AccountStatus.CREATED));
    }


    //fonction d'evolution
    @EventSourcingHandler //initialise l'etat de l'agregat
    public void on(AccountCreatedEvent event){
        System.out.println("EVENT SOURCING HANDLER EXECUTED");
        log.info("############# EventSourcingHandler : AccountCreatedEvent occured ########################## ");
         this.accountId=event.getId();
         this.accountStatus=event.getAccountStatus();
         this.balance=event.getInitialBalance();
    }

    //Voici le fonctionnement :
    //1. CommandGateway envoie la commande.
    //2. Le CommandBus reçoit et distribue la commande.
    //3. Axon recherche le CommandHandler correspondant au type de commande.
    //4. Le constructeur annoté avec @CommandHandler reçoit AddAccountCommand et initialise l'Aggregate.

    //Comment  trouver le Handler approprié :
    //@TargetAggregateIdentifier pour identifier l'Aggregate cible lorsqu'il s'agit d'une commande destinée à un Aggregate existant.
}
