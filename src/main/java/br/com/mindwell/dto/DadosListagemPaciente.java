package br.com.mindwell.dto;

import br.com.mindwell.model.Paciente;

import java.time.LocalDate;
import java.util.UUID;

public record DadosListagemPaciente(UUID id, String nome, String email, LocalDate dataNascimento) {

    // Construtor adicional que converte um Paciente em DadosListagemPaciente
    public DadosListagemPaciente(Paciente Paciente) {
        this(Paciente.getId(), Paciente.getNome(), Paciente.getEmail(), Paciente.getDataNascimento());
    }
}