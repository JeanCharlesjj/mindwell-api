package br.com.mindwell.dto;

import br.com.mindwell.enums.StatusConsulta;
import br.com.mindwell.model.Consulta;

import java.time.LocalDateTime;
import java.util.UUID;

public record DadosDetalhamentoConsulta(UUID id, UUID idPsicologo, UUID idPaciente, LocalDateTime dataHora, StatusConsulta status) {
    public DadosDetalhamentoConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getPsicologo().getId(), consulta.getPaciente().getId(), consulta.getDataHora(), consulta.getStatus());
    }
}
