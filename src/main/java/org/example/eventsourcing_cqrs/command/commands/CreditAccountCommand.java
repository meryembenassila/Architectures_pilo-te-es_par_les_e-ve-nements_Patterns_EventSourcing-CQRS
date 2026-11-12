package org.example.eventsourcing_cqrs.command.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
@Getter @AllArgsConstructor
public class CreditAccountCommand  {
    @TargetAggregateIdentifier
    private String id;
    private double ammount ;
    private String currency ;


}
