package br.com.mindwell.controller;

import br.com.mindwell.dto.DadosAgendamentoConsulta;
import br.com.mindwell.dto.DadosDetalhamentoConsulta;
import br.com.mindwell.model.Consulta;
import br.com.mindwell.service.ConsultaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @PostMapping("/psicologos/{idPsicologo}/agendar")
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> agendarPeloPsicologo(
            @PathVariable UUID idPsicologo,
            @RequestBody DadosAgendamentoConsulta dados) {

        Consulta consultaAgendada = consultaService.agendar(dados, idPsicologo);
        return ResponseEntity.ok(new DadosDetalhamentoConsulta(consultaAgendada));
    }
}