package br.com.mindwell.controller;

import br.com.mindwell.dto.DadosAtualizacaoPaciente;
import br.com.mindwell.dto.DadosCadastroPaciente;
import br.com.mindwell.dto.DadosListagemPaciente;
import br.com.mindwell.model.Paciente;
import br.com.mindwell.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

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
}
