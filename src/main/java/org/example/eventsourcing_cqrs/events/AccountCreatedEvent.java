package org.example.eventsourcing_cqrs.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.eventsourcing_cqrs.enums.AccountStatus;
@Getter @AllArgsConstructor
@NoArgsConstructor//evenement c'est un objet umutable
public class AccountCreatedEvent {
    private String id;
    private double initialBalance;
    private String currency;
    private AccountStatus accountStatus;
}
