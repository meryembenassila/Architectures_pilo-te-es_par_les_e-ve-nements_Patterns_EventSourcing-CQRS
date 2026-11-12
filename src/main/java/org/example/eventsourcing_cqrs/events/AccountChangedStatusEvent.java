package org.example.eventsourcing_cqrs.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.eventsourcing_cqrs.enums.AccountStatus;
@Getter @AllArgsConstructor
public class AccountChangedStatusEvent {
    private String  accountId;
    private AccountStatus accountStatus;


}
