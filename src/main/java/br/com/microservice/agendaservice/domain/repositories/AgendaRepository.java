package br.com.microservice.agendaservice.domain.repositories;

import br.com.microservice.agendaservice.domain.entity.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    Optional<Agenda> findByHorario(OffsetDateTime horario);
}
