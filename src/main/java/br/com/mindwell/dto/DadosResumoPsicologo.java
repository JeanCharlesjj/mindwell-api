package br.com.mindwell.dto;

import br.com.mindwell.model.Psicologo;
import java.util.UUID;

public record DadosResumoPsicologo(UUID id, String nome) {
    public DadosResumoPsicologo(Psicologo psicologo) {
        this(psicologo.getId(), psicologo.getNome());
    }
}