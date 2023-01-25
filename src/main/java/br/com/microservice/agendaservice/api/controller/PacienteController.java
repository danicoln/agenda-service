package br.com.microservice.agendaservice.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.microservice.agendaservice.api.mapper.PacienteMapper;
import br.com.microservice.agendaservice.api.request.PacienteRequest;
import br.com.microservice.agendaservice.api.response.PacienteResponse;
import br.com.microservice.agendaservice.domain.entity.Paciente;
import br.com.microservice.agendaservice.domain.services.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

	private final PacienteService service;
	private final PacienteMapper mapper;
	
	@PostMapping
	public ResponseEntity<PacienteResponse> salvar(@Valid @RequestBody PacienteRequest request){
		
		Paciente paciente = mapper.toPaciente(request);
		Paciente pacienteSalvo = service.salvar(paciente);
		PacienteResponse response = mapper.toPacienteResponse(pacienteSalvo);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping
	public ResponseEntity<List<PacienteResponse>> listarTodos(){
		List<Paciente> pacientes = service.listarTodos();
		List<PacienteResponse> pacienteResponses = mapper.toPacienteResponseList(pacientes);
		return ResponseEntity.status(HttpStatus.OK).body(pacienteResponses);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PacienteResponse> listarPorId(@Valid @PathVariable Long id){
		Optional<Paciente> paciente = service.buscarPorId(id);
		if(paciente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(mapper.toPacienteResponse(paciente.get()));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PacienteResponse> alterar(@Valid @PathVariable Long id, @RequestBody PacienteRequest request){
		Paciente paciente = mapper.toPaciente(request);
		Paciente pacienteSalvo = service.alterar(id, paciente);
		PacienteResponse pacienteResponse = mapper.toPacienteResponse(pacienteSalvo);
		return ResponseEntity.status(HttpStatus.OK).body(pacienteResponse);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@Valid @PathVariable Long id){
		service.deletar(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
	}
}
