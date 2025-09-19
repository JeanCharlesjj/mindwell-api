package br.com.mindwell.repository;

import br.com.mindwell.model.Psicologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PsicologoRepository extends JpaRepository<Psicologo, UUID> {
}