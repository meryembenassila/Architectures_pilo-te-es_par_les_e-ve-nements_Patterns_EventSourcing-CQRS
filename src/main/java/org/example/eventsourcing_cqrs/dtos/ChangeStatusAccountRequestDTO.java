package org.example.eventsourcing_cqrs.dtos;

import lombok.Getter;
import org.example.eventsourcing_cqrs.enums.AccountStatus;
@Getter
public class ChangeStatusAccountRequestDTO {
    private String  accountId;
    private AccountStatus accountStatus;
}
