package com.devpenha.clients.controllers;

import com.devpenha.clients.dto.ClientDto;
import com.devpenha.clients.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;


    @GetMapping
    public ResponseEntity<Page<ClientDto>> findAll(Pageable pageable){
        return ResponseEntity.ok(clientService.findAll(pageable));
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(clientService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ClientDto> insert(@RequestBody @Valid ClientDto clientDto){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(clientDto.id()).toUri();
        return ResponseEntity.created(uri).body(clientService.insert(clientDto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientDto> update(@PathVariable Long id,
                                            @RequestBody @Valid ClientDto clientDto){
        return  ResponseEntity.ok(clientService.update(id, clientDto));
    }

    @DeleteMapping(value =  "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
