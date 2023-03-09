package com.example.controller;

import com.example.entity.Client;
import com.example.model.RecordNotFoundException;
import com.example.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService service;

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> list = service.getAllClients();

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable("id") Long id) throws RecordNotFoundException {
        Client entity = service.getClientById(id);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Client> createOrUpdateClient(Client client) {
        Client updated = service.createOrUpdateClient(client);
        return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteClientById(@PathVariable("id") Long id) throws RecordNotFoundException {
        service.deleteClientById(id);
        return HttpStatus.FORBIDDEN;
    }

}