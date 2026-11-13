package org.example.eventsourcing_cqrs.query.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.eventsourcing_cqrs.enums.AccountStatus;

import java.time.Instant;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    private String id;
    private Instant createdAt;//Instant car on va le recuperer de la date de evnt créer
    private double balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String currency;


}
