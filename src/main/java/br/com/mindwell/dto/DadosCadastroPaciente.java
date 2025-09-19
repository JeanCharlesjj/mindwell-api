package br.com.mindwell.dto;

import java.time.LocalDate;

public record DadosCadastroPaciente(String nome, String email, String senha, LocalDate dataNascimento) {
}
