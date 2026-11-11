package org.example.eventsourcing_cqrs.command.controllers;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.eventsourcing_cqrs.command.commands.AddAccountCommand;
import org.example.eventsourcing_cqrs.dtos.AddNewAccountRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/commands")
public class AccountCommandController {
    @Autowired
     private CommandGateway commandGateway; // Dans Axon Framework, CommandGateway est un objet fourni par le framework qui permet d'envoyer (dispatcher) une commande vers le Command Bus.
    @PostMapping("/add")
    public  CompletableFuture<String>  AddNewAccount(@RequestBody AddNewAccountRequestDTO request){
        CompletableFuture<String> send = commandGateway.send(new AddAccountCommand(UUID.randomUUID().toString(), request.initialBalance(), request.currency()));// il va retourner le id de l'agregat cad le comte créer
        return send;

    }
    @ExceptionHandler(Exception.class)
    // @ExceptionHandler(Exception.class) : agit sur une méthode et indique quelle exception cette méthode doit intercepter.
    public  String exceptionHandler(Exception exception){
        return exception.getMessage();
    }

}
