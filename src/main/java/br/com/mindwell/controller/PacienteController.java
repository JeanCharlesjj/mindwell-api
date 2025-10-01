package br.com.mindwell.controller;

import br.com.mindwell.dto.*;
import br.com.mindwell.model.Consulta;
import br.com.mindwell.model.Paciente;
import br.com.mindwell.model.Psicologo;
import br.com.mindwell.repository.PacienteRepository;
import br.com.mindwell.repository.PsicologoRepository;
import br.com.mindwell.service.ConsultaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @Autowired
    private PsicologoRepository psicologoRepository;

    @Autowired
    private ConsultaService consultaService;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroPaciente dados) {
        Paciente novoPaciente = new Paciente();
        novoPaciente.setNome(dados.nome());
        novoPaciente.setEmail(dados.email());
        novoPaciente.setSenha(dados.senha());
        novoPaciente.setDataNascimento(dados.dataNascimento());

        repository.save(novoPaciente);
    }

    @GetMapping
    public List<DadosListagemPaciente> listar() {
        return repository.findAll().stream().map(DadosListagemPaciente::new).toList();
    }

    @PutMapping("/{id}")
    @Transactional
    public void atualizar(@PathVariable UUID id, @RequestBody DadosAtualizacaoPaciente dados) {
        Paciente Paciente = repository.getReferenceById(id);
        Paciente.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletar(@PathVariable UUID id) {
        repository.deleteById(id);
    }

    @PostMapping("/{idPaciente}/associar")
    @Transactional
    public ResponseEntity<Void> associarPsicologo(@PathVariable UUID idPaciente, @RequestBody DadosAssociacaoPaciente dados) {
        Paciente paciente = repository.findById(idPaciente)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Psicologo psicologo = psicologoRepository.findByCodigoDeAssociacao(dados.codigoDeAssociacao())
                .orElseThrow(() -> new RuntimeException("Psicólogo não encontrado com o código informado"));

        paciente.setPsicologo(psicologo);

        repository.save(paciente);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{idPaciente}/consultas")
    public ResponseEntity<List<DadosDetalhamentoConsulta>> listarConsultas(@PathVariable UUID idPaciente) {
        List<Consulta> consultas = consultaService.buscarPorPaciente(idPaciente);

        List<DadosDetalhamentoConsulta> dtos = consultas.stream()
                .map(DadosDetalhamentoConsulta::new)
                .toList();

        return ResponseEntity.ok(dtos);
    }
}
