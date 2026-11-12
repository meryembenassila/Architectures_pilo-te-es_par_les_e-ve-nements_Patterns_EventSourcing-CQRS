package org.example.eventsourcing_cqrs.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class AccountDebitedEvent {
        private String id;
        private double amount;
        private String currency;

    }
