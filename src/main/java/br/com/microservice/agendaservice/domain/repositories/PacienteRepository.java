package br.com.microservice.agendaservice.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.microservice.agendaservice.domain.entity.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long>{

	Optional<Paciente> findByCpf(String cpf);
	
}
