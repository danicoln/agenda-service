package br.com.microservice.agendaservice.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode estar vazio!")
    private String nome;

    @NotBlank(message = "Favor inserir o usuário")
    private String usuario;

    @NotBlank(message = "Favor inserir a senha")
    private String senha;
}
