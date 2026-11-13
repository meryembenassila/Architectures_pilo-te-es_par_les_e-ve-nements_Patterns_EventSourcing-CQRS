package org.example.eventsourcing_cqrs.aggregate;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.eventsourcing_cqrs.command.commands.AddAccountCommand;
import org.example.eventsourcing_cqrs.command.commands.CreditAccountCommand;
import org.example.eventsourcing_cqrs.command.commands.DebitAccountCommand;
import org.example.eventsourcing_cqrs.command.commands.ChangeStatusAccountCommand;
import org.example.eventsourcing_cqrs.enums.AccountStatus;
import org.example.eventsourcing_cqrs.events.*;

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
    public AccountAggregate(AddAccountCommand command){ //!!!! initialise l'etat de l'agregat
        System.out.println("COMMAND HANDLER EXECUTED");
        log.info("############# CommandHandler : AddAccountCommand created ########################## ");
      if(command.getInitialBalance()<=0) throw  new IllegalArgumentException("Balance must be positive");
      AggregateLifecycle.apply(new AccountCreatedEvent(command.getId(),command.getInitialBalance(),command.getCurrency(),AccountStatus.CREATED));
        AggregateLifecycle.apply(new AccountActivatedEvent(command.getId(),AccountStatus.ACTIVATED));//ici un fonction de decision peut emetre plusieures evenements
    }


    //fonction d'evolution
    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        System.out.println("EVENT SOURCING HANDLER EXECUTED");
        log.info("############# EventSourcingHandler : AccountCreatedEvent occured ########################## ");
         this.accountId=event.getId();
         this.accountStatus=event.getAccountStatus();
         this.balance=event.getInitialBalance();
    }


    @CommandHandler
    public void handle(CreditAccountCommand command){
        System.out.println("COMMAND HANDLER EXECUTED");
        log.info("############# CommandHandler : CreditAccountCommand created ########################## ");
        if(!accountStatus.equals(AccountStatus.ACTIVATED)) throw  new RuntimeException("The Account Is not acctivated" );
        if(command.getAmmount()<=0) throw  new IllegalArgumentException("Amount must be positive");
        AggregateLifecycle.apply(new AccountCreditedEvent(this.accountId, command.getAmmount(), command.getCurrency()));
    }



    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        System.out.println("EVENT SOURCING HANDLER EXECUTED");
        log.info("############# EventSourcingHandler : AccountCreatedEvent occured ########################## ");
        //this.accountId = event.getId();
        this.balance=this.balance + event.getAmount();
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent event){
        System.out.println("EVENT SOURCING HANDLER EXECUTED");
        log.info("############# EventSourcingHandler : AccountActivatedEvent occured ########################## ");
        // this.accountId = event.getAccountId();
        this.accountStatus=event.getAccountStatus();
    }


    @CommandHandler
    public void handle(DebitAccountCommand command){
        System.out.println("COMMAND HANDLER EXECUTED");
        log.info("############# CommandHandler : DebitAccountCommand created ########################## ");
        if(!accountStatus.equals(AccountStatus.ACTIVATED)) throw  new RuntimeException("The Account Is not acctivated" );
        if(this.balance<command.getAmmount()) throw  new IllegalArgumentException("Balance Not suffiecent Exception");
        if(command.getAmmount()<=0) throw  new IllegalArgumentException("Amount must be positive");
        AggregateLifecycle.apply(new AccountDebitedEvent(command.getId(), command.getAmmount(), command.getCurrency()));
    }



    @EventSourcingHandler
    public void on(AccountDebitedEvent event){
        System.out.println("EVENT SOURCING HANDLER EXECUTED");
        log.info("############# EventSourcingHandler : AccountDebitedEvent occured ########################## ");
        //this.accountId = event.getId();
        this.balance=this.balance - event.getAmount();}



    @CommandHandler
    public void handle(ChangeStatusAccountCommand command){
        System.out.println("COMMAND HANDLER EXECUTED");
        log.info("############# CommandHandler : ChangeStatusAccountCommand created ########################## ");

        AggregateLifecycle.apply(new AccountChangedStatusEvent(command.getAccountId(), command.getAccountStatus()));
    }



    @EventSourcingHandler
    public void on(AccountChangedStatusEvent event){
        System.out.println("EVENT SOURCING HANDLER EXECUTED");
        log.info("############# EventSourcingHandler : AccountChangedStatusEvent occured ########################## ");
        //this.accountId = event.getId();
        this.accountStatus=event.getAccountStatus();}

    //Voici le fonctionnement :
    //1. CommandGateway envoie la commande.
    //2. Le CommandBus reçoit et distribue la commande.
    //3. Axon recherche le CommandHandler correspondant au type de commande.
    //4. Le constructeur annoté avec @CommandHandler reçoit AddAccountCommand et initialise l'Aggregate.

    //Comment  trouver le Handler approprié :
    //@TargetAggregateIdentifier pour identifier l'Aggregate cible lorsqu'il s'agit d'une commande destinée à un Aggregate existant.
}
