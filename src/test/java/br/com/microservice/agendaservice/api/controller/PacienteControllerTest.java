package br.com.microservice.agendaservice.api.controller;

import br.com.microservice.agendaservice.api.request.PacienteRequest;
import br.com.microservice.agendaservice.domain.entity.Paciente;
import br.com.microservice.agendaservice.domain.repositories.PacienteRepository;
import br.com.microservice.agendaservice.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/*Teste de Integração com MockMvc
*
* O teste consiste em criar um paciente em memória usando o bd H2 com a anotação @BeforeEach e apagar os dados com @AfterEach*/

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class PacienteControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PacienteRepository repository;

    @BeforeEach
    void up(){
        Paciente paciente = new Paciente();
        paciente.setNome("Marta");
        paciente.setSobrenome("Scweinsteinger");
        paciente.setCpf("12365498770");
        paciente.setEmail("marta.sc@gmail.com");
        repository.save(paciente);
    }

    @AfterEach
    void down(){
        repository.deleteAll();
    }

    @Test
    @DisplayName("Listar todos os pacientes")
    void listarPacientes() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/pacientes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }
    @Test
    @DisplayName("Salva paciente com sucesso")
    void salvarPaciente() throws Exception {

        PacienteRequest paciente = PacienteRequest.builder()
                .email("john@gmail.com")
                .nome("John")
                .sobrenome("silva")
                .cpf("23465498710")
                .build();
        String pacienteRequest = mapper.writeValueAsString(paciente);

        mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                .content(pacienteRequest))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Salva paciente com sucesso")
    void salvarPacienteComCpfExistente() throws Exception {

        PacienteRequest paciente = PacienteRequest.builder()
                .email("tobias@gmail.com")
                .nome("Tobias com Mesmo Cpf Silva")
                .sobrenome("silva")
                .cpf("12365498770")
                .build();
        String pacienteRequest = mapper.writeValueAsString(paciente);

        mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(pacienteRequest))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof BusinessException))
                .andDo(MockMvcResultHandlers.print());
    }
}