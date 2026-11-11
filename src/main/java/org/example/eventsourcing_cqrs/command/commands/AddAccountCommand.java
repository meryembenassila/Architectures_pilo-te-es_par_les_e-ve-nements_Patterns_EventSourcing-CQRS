package org.example.eventsourcing_cqrs.command.commands;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

//une commande est un objet immutable contient juste des getteers // a voir pourquoi
@Getter
@AllArgsConstructor
public class AddAccountCommand {
    @TargetAggregateIdentifier
    private String id;
    private double initialBalance;
    private String currency;
}
