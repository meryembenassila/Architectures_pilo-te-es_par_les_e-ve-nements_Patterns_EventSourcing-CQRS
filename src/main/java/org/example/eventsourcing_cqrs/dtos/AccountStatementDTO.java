package org.example.eventsourcing_cqrs.dtos;

import org.example.eventsourcing_cqrs.query.entities.Account;
import org.example.eventsourcing_cqrs.query.entities.Operation;

import java.util.List;

public record AccountStatementDTO (Account account,List<Operation> operations)  {

}
