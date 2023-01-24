package br.com.microservice.agendaservice.domain.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.microservice.agendaservice.domain.entity.Paciente;
import br.com.microservice.agendaservice.domain.repositories.PacienteRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PacienteService {

	private PacienteRepository repository;
	
	public Paciente salvar(Paciente paciente) {
		
		//ToDo: para validar se o cpf existe
		
		return repository.save(paciente);
	}
	
	public List<Paciente> listarTodos(){
		return repository.findAll();
	}
	
	public Optional<Paciente> buscarPorId(Long id) {
		return repository.findById(id);
	}
	
	public void deletar(Long id) {
		repository.deleteById(id);
	}
}
