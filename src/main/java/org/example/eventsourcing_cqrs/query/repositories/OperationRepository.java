package org.example.eventsourcing_cqrs.query.repositories;

import org.example.eventsourcing_cqrs.query.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation,Long> {
    List<Operation> findByAccountId(String accountId);
}