package br.com.mindwell.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record DadosAgendamentoConsulta(UUID idPsicologo, UUID idPaciente, LocalDateTime dataHora) {

}
