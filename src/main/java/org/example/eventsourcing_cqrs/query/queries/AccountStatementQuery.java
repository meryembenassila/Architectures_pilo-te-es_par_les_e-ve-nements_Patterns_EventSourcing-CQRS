package org.example.eventsourcing_cqrs.query.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccountStatementQuery {
    private String accountId;
}
