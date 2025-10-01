package br.com.mindwell.service;

import br.com.mindwell.dto.DadosAgendamentoConsulta;
import br.com.mindwell.dto.DadosAnotacaoConsulta;
import br.com.mindwell.model.Consulta;
import br.com.mindwell.model.Paciente;
import br.com.mindwell.model.Psicologo;
import br.com.mindwell.enums.StatusConsulta;
import br.com.mindwell.repository.ConsultaRepository;
import br.com.mindwell.repository.PacienteRepository;
import br.com.mindwell.repository.PsicologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PsicologoRepository psicologoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public Consulta agendar(DadosAgendamentoConsulta dados, UUID idPsicologo) {
        // 1. Busca as entidades do banco de dados
        Psicologo psicologo = psicologoRepository.findById(idPsicologo)
                .orElseThrow(() -> new RuntimeException("Psicólogo não encontrado"));
        Paciente paciente = pacienteRepository.findById(dados.idPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        // 2. REGRA DE NEGÓCIO: Verifica a associação
        if (paciente.getPsicologo() == null || !paciente.getPsicologo().getId().equals(psicologo.getId())) {
            throw new RuntimeException("Agendamento não permitido: O paciente não está associado a este psicólogo.");
        }

        // 3. Cria a nova consulta com o status PENDENTE
        Consulta novaConsulta = new Consulta();
        novaConsulta.setPsicologo(psicologo);
        novaConsulta.setPaciente(paciente);
        novaConsulta.setDataHora(dados.dataHora());
        novaConsulta.setStatus(StatusConsulta.PENDENTE_ACEITE);

        // 4. Salva no banco e retorna
        return consultaRepository.save(novaConsulta);
    }

    public Consulta aceitar(UUID idConsulta) {
        Consulta consulta = consultaRepository.findById(idConsulta)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        if (consulta.getStatus() != StatusConsulta.PENDENTE_ACEITE) {
            throw new RuntimeException("Apenas consultas com status PENDENTE_ACEITE podem ser aceitas.");
        }

        consulta.setStatus(StatusConsulta.AGENDADA);
        return consultaRepository.save(consulta);
    }

    public Consulta recusar(UUID idConsulta) {
        Consulta consulta = consultaRepository.findById(idConsulta)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        if (consulta.getStatus() != StatusConsulta.PENDENTE_ACEITE) {
            throw new RuntimeException("Apenas consultas com status PENDENTE_ACEITE podem ser recusadas.");
        }

        consulta.setStatus(StatusConsulta.CANCELADA);
        return consultaRepository.save(consulta);
    }

    public List<Consulta> buscarPorPsicologo(UUID psicologoId) {
        return consultaRepository.findByPsicologoId(psicologoId);
    }

    // NOSSO NOVO MÉTODO:
    public List<Consulta> buscarPorPaciente(UUID pacienteId) {
        return consultaRepository.findByPacienteId(pacienteId);
    }

    public void salvarAnotacao(UUID idConsulta, DadosAnotacaoConsulta dados) {
        Consulta consulta = consultaRepository.findById(idConsulta)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        consulta.setAnotacao(dados.texto());
    }
}