package br.com.mindwell.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "psicologos")
@Getter
@Setter
public class Psicologo { // Futuramente, podemos fazer herdar de uma classe Usuario

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String senha; // Vamos criptografar isso depois!

    private String crp; // Conselho Regional de Psicologia

    @Column(unique = true)
    private String codigoDeAssociacao;

    // Outros campos...
    // Outros campos...
}
