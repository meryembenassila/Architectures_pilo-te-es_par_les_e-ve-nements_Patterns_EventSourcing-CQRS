package org.example.eventsourcing_cqrs.command.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.example.eventsourcing_cqrs.enums.AccountStatus;
@Getter @AllArgsConstructor

public class ChangeStatusAccountCommand {
    @TargetAggregateIdentifier
    private String  accountId;
    private AccountStatus accountStatus;
}
