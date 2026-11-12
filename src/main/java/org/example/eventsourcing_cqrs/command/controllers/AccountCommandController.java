package org.example.eventsourcing_cqrs.command.controllers;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.eventsourcing_cqrs.command.commands.AddAccountCommand;
import org.example.eventsourcing_cqrs.dtos.AddNewAccountRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands")
@Slf4j
public class AccountCommandController {

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private EventStore eventStore;

    @PostMapping("/add")
    public CompletableFuture<String> AddNewAccount(
            @RequestBody AddNewAccountRequestDTO request) {

        AddAccountCommand command = new AddAccountCommand(
                UUID.randomUUID().toString(),
                request.initialBalance(),
                request.currency()
        );
        CompletableFuture<String> result = commandGateway.send(command);

        return result;
    }

    // Cette fonction permet de récupérer tous les événements associés à un compte
     // à partir de son identifiant, puis de les retourner sous forme de Stream.
    @GetMapping("/events/{accountId}")
    public Stream eventStore(@PathVariable String accountId) {
        return eventStore.readEvents(accountId).asStream();
    }

}
