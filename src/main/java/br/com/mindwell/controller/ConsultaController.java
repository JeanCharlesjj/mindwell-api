package br.com.mindwell.controller;

import br.com.mindwell.dto.DadosAgendamentoConsulta;
import br.com.mindwell.dto.DadosDetalhamentoConsulta;
import br.com.mindwell.enums.StatusConsulta;
import br.com.mindwell.model.Consulta;
import br.com.mindwell.model.Paciente;
import br.com.mindwell.model.Psicologo;
import br.com.mindwell.repository.ConsultaRepository;
import br.com.mindwell.repository.PacienteRepository;
import br.com.mindwell.repository.PsicologoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PsicologoRepository psicologoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> agendar(@RequestBody DadosAgendamentoConsulta dados) {
        // Validação simples para garantir que o psicólogo e o paciente existem
        Psicologo psicologo = psicologoRepository.findById(dados.idPsicologo())
                .orElseThrow(() -> new RuntimeException("Psicólogo não encontrado"));
        Paciente paciente = pacienteRepository.findById(dados.idPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        // Cria a nova consulta
        Consulta novaConsulta = new Consulta();
        novaConsulta.setPsicologo(psicologo);
        novaConsulta.setPaciente(paciente);
        novaConsulta.setDataHora(dados.dataHora());
        novaConsulta.setStatus(StatusConsulta.AGENDADA);

        // Salva no banco de dados
        consultaRepository.save(novaConsulta);

        // Retorna os detalhes da consulta criada
        return ResponseEntity.ok(new DadosDetalhamentoConsulta(novaConsulta));
    }
}
