package br.com.mindwell.dto;

import br.com.mindwell.model.Psicologo;
import java.util.UUID;

public record DadosListagemPsicologo(UUID id, String nome, String email, String crp) {

    // Construtor adicional que converte um Psicologo em DadosListagemPsicologo
    public DadosListagemPsicologo(Psicologo psicologo) {
        this(psicologo.getId(), psicologo.getNome(), psicologo.getEmail(), psicologo.getCrp());
    }
}