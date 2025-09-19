package br.com.mindwell.controller;

import br.com.mindwell.dto.DadosAtualizacaoPsicologo;
import br.com.mindwell.dto.DadosCadastroPsicologo;
import br.com.mindwell.dto.DadosListagemPsicologo;
import br.com.mindwell.model.Psicologo;
import br.com.mindwell.repository.PsicologoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/psicologos")
public class PsicologoController {

    @Autowired
    private PsicologoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroPsicologo dados) {
        Psicologo novoPsicologo = new Psicologo();
        novoPsicologo.setNome(dados.nome());
        novoPsicologo.setEmail(dados.email());
        novoPsicologo.setSenha(dados.senha());
        novoPsicologo.setCrp(dados.crp());
        novoPsicologo.setCodigoDeAssociacao(UUID.randomUUID().toString().substring(0, 8));

        repository.save(novoPsicologo);
    }

    @GetMapping
    public List<DadosListagemPsicologo> listar() {
        return repository.findAll().stream().map(DadosListagemPsicologo::new).toList();
    }

    @PutMapping("/{id}")
    @Transactional
    public void atualizar(@PathVariable UUID id, @RequestBody DadosAtualizacaoPsicologo dados) {
        Psicologo psicologo = repository.getReferenceById(id);
        psicologo.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletar(@PathVariable UUID id) {
        repository.deleteById(id);
    }
}