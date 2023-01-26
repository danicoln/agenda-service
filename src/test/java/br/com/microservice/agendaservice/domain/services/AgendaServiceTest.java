package br.com.microservice.agendaservice.domain.services;

import br.com.microservice.agendaservice.domain.entity.Agenda;
import br.com.microservice.agendaservice.domain.entity.Paciente;
import br.com.microservice.agendaservice.domain.repositories.AgendaRepository;
import br.com.microservice.agendaservice.exception.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AgendaServiceTest {

    @InjectMocks
    AgendaService service;

    @Mock
    PacienteService pacienteService;
    @Mock
    AgendaRepository repository;

    @Captor
    ArgumentCaptor<Agenda> agendaCaptor;

    @Test
    @DisplayName("Deve salvar agendamento com sucesso")
    void salvarComSucesso() {

        // setup
        // - Arrange
        LocalDateTime now = LocalDateTime.now();
        Agenda agenda = new Agenda();
        agenda.setDescricao("Descrição do agendamento");
        agenda.setHorario(now);

        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("Paciente");

        agenda.setPaciente(paciente);

        Mockito.when(pacienteService.buscarPorId(agenda.getPaciente().getId()))
                .thenReturn(Optional.of(paciente));

        Mockito.when(repository.findByHorario(now))
                .thenReturn(Optional.empty());

        //teste
        //- Action
        service.salvar(agenda);

        //validações
        //-Assertions

        /*Testa se o pacienteService buscarPorId foi chamado, passando agenda.getPaciente().getId()*/
        Mockito.verify(pacienteService).buscarPorId(agenda.getPaciente().getId());

        Mockito.verify(repository).findByHorario(now);

        Mockito.verify(repository).save(agendaCaptor.capture());

        Agenda agendaSalva = agendaCaptor.getValue();
        Assertions.assertThat(agendaSalva.getDataCriacao()).isNotNull();
        Assertions.assertThat(agendaSalva.getPaciente()).isNotNull();

    }

    @Test
    @DisplayName("Não deve salvar agendamento sem paciente")
    void salvarErrorPacienteNaoEncontrado() {

        // setup
        // - Arrange
        LocalDateTime now = LocalDateTime.now();
        Agenda agenda = new Agenda();
        agenda.setDescricao("Descrição do agendamento");
        agenda.setHorario(now);

        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("Paciente");

        agenda.setPaciente(paciente);

        Mockito.when(pacienteService.buscarPorId(ArgumentMatchers.any()))
                .thenReturn(Optional.empty());

        //teste
        //- Action
        BusinessException businessException = assertThrows(BusinessException.class, () -> {

            service.salvar(agenda);
        });

        //validações
        //-Assertions
        Assertions.assertThat(businessException.getMessage())
                .isEqualTo("Paciente não encontrado");


    }
}