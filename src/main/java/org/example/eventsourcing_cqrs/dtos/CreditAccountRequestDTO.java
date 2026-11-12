package org.example.eventsourcing_cqrs.dtos;

public record CreditAccountRequestDTO(String accountId, double ammount , String currency){}