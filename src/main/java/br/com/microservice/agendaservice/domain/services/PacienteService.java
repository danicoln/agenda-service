package br.com.microservice.agendaservice.domain.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.microservice.agendaservice.domain.entity.Paciente;
import br.com.microservice.agendaservice.domain.repositories.PacienteRepository;
import br.com.microservice.agendaservice.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PacienteService {

	@Autowired
	private PacienteRepository repository;
	
	public Paciente salvar(Paciente paciente) {
		//ToDo: para validar se o cpf existe
		boolean existeCpf = false;
		
		Optional<Paciente> optPaciente = repository.findByCpf(paciente.getCpf());
		if(optPaciente.isPresent()) {
			if(!optPaciente.get().getId().equals(paciente.getId())) {
				existeCpf = true;
			}
		}
		if(existeCpf) {
			throw new BusinessException("Cpf já cadastrado");
		}
		return repository.save(paciente);
	}

	public Paciente alterar(Long id, Paciente paciente){
		Optional<Paciente> optPaciente = this.buscarPorId(id);

		if(optPaciente.isEmpty()){
			throw new BusinessException("Paciente não cadastrado");
		}
		paciente.setId(id);
		return salvar(paciente);
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
