package com.devpenha.clients.dto;

import com.devpenha.clients.entities.Client;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record ClientDto(
        Long id,
        @NotBlank(message = "nome não pode ser vazio")
        String name,
        String cpf,
        @Positive
        Double income,
        @PastOrPresent(message = "data de nascimento: não pode ser data futura")
        LocalDate birthDate,
        Integer children

) {
        public ClientDto(Client client){
                this(client.getId(), client.getName(), client.getCpf(),client.getIncome(),client.getBirthDate(), client.getChildren() );
        }
}
