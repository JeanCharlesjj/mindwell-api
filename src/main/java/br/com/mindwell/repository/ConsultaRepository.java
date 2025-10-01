package br.com.mindwell.repository;

import br.com.mindwell.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, UUID> {

    List<Consulta> findByPsicologoId(UUID psicologoId);

    List<Consulta> findByPacienteId(UUID pacienteId);
}
