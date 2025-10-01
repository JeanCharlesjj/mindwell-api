package br.com.mindwell.controller;

import br.com.mindwell.dto.DadosAgendamentoConsulta;
import br.com.mindwell.dto.DadosAnotacaoConsulta;
import br.com.mindwell.dto.DadosDetalhamentoConsulta;
import br.com.mindwell.dto.RespostaSimples;
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


    @PostMapping("/{idConsulta}/aceitar")
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> aceitar(@PathVariable UUID idConsulta) {
        Consulta consultaAtualizada = consultaService.aceitar(idConsulta);
        return ResponseEntity.ok(new DadosDetalhamentoConsulta(consultaAtualizada));
    }

    @PostMapping("/{idConsulta}/recusar")
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> recusar(@PathVariable UUID idConsulta) {
        Consulta consultaAtualizada = consultaService.recusar(idConsulta);
        return ResponseEntity.ok(new DadosDetalhamentoConsulta(consultaAtualizada));
    }

    @PutMapping("/{idConsulta}/anotacao")
    @Transactional
    public ResponseEntity<RespostaSimples> salvarAnotacao(@PathVariable UUID idConsulta, @RequestBody DadosAnotacaoConsulta dados) {
        consultaService.salvarAnotacao(idConsulta, dados);
        return ResponseEntity.ok(new RespostaSimples("Anotação salva com sucesso!"));
    }
}