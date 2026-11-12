package org.example.eventsourcing_cqrs.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.eventsourcing_cqrs.enums.AccountStatus;
@AllArgsConstructor
@Getter
public class AccountActivatedEvent {
  private String accountId;
  private AccountStatus accountStatus;


}
