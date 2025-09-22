package br.com.mindwell.dto;

import br.com.mindwell.model.Paciente;
import java.time.LocalDate;
import java.util.UUID;

public record DadosListagemPaciente(
        UUID id,
        String nome,
        String email,
        String telefone,
        LocalDate dataNascimento,
        DadosResumoPsicologo psicologo
) {
    public DadosListagemPaciente(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getTelefone(),
                paciente.getDataNascimento(),
                paciente.getPsicologo() != null ? new DadosResumoPsicologo(paciente.getPsicologo()) : null
        );
    }
}