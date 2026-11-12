package org.example.eventsourcing_cqrs.dtos;



public record DebitAccountRequestDTO(String accountId, double ammount , String currency){}