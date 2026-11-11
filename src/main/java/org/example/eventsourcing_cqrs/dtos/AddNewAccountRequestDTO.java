package org.example.eventsourcing_cqrs.dtos;

public record AddNewAccountRequestDTO(double initialBalance, String currency) {
}
