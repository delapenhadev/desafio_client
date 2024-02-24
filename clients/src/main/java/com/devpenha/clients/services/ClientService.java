package com.devpenha.clients.services;

import com.devpenha.clients.dto.ClientDto;
import com.devpenha.clients.entities.Client;
import com.devpenha.clients.exceptions.DatabaseException;
import com.devpenha.clients.exceptions.ResourceNotFoundException;
import com.devpenha.clients.respositories.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.websocket.ClientEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDto> findAll(Pageable pageable){
        return repository.findAll(pageable)
                .map(ClientDto::new);
    }

    @Transactional(readOnly = true)
    public ClientDto findById(Long id){
        Client client =  repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id Not Found"));
        return new ClientDto(client);
    }

    @Transactional
    public ClientDto insert(ClientDto clientDto){
        var client = new Client();
        entityToDto(client, clientDto);
        client = repository.save(client);
        return new ClientDto(client);
    }

    @Transactional
    public ClientDto update(Long id, ClientDto clientDto){
        try {
            var client = repository.getReferenceById(id);
            entityToDto(client, clientDto);
            client = repository.save(client);
            return new ClientDto(client);
        }catch (EntityNotFoundException ex){
            throw  new ResourceNotFoundException("Resource Not Found");
        }
    }

    @Transactional
    public void delete(Long id){
        if(! repository.existsById(id)){
            throw  new ResourceNotFoundException("Resource Not Found");
        }
        try{
            repository.deleteById(id);
        }catch (DataIntegrityViolationException ex){
            throw new DatabaseException("Fail Entity Referencial");
        }
    }

    public void entityToDto(Client client, ClientDto clientDto){
        client.setName(clientDto.name());
        client.setCpf(clientDto.cpf());
        client.setIncome(clientDto.income());
        client.setBirthDate(clientDto.birthDate());
        client.setChildren(clientDto.children());
    }
}
