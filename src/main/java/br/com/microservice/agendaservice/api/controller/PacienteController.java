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
	
	@PostMapping
	public ResponseEntity<PacienteResponse> salvar(@Valid @RequestBody PacienteRequest request){
		
		Paciente paciente = PacienteMapper.toPaciente(request);
		Paciente pacienteSalvo = service.salvar(paciente);
		PacienteResponse response = PacienteMapper.toPacienteResponse(pacienteSalvo);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping
	public ResponseEntity<List<Paciente>> listarTodos(){
		List<Paciente> pacientes = service.listarTodos();
		return ResponseEntity.status(HttpStatus.OK).body(pacientes);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Paciente> listarPorId(@PathVariable Long id){
		Optional<Paciente> paciente = service.buscarPorId(id);
		if(paciente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(paciente.get());
	}
	
	@PutMapping
	public ResponseEntity<Paciente> alterar(@RequestBody Paciente paciente){
		Paciente pacienteSalvo = service.salvar(paciente);
		
		return ResponseEntity.status(HttpStatus.OK).body(pacienteSalvo);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id){
		service.deletar(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
	}
}
