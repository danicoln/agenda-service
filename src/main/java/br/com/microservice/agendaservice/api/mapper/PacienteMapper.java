package br.com.microservice.agendaservice.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.microservice.agendaservice.api.request.PacienteRequest;
import br.com.microservice.agendaservice.api.response.PacienteResponse;
import br.com.microservice.agendaservice.domain.entity.Paciente;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PacienteMapper {
	
	private final ModelMapper mapper;
	
	public Paciente toPaciente(PacienteRequest request) {
		return mapper.map(request, Paciente.class);
	}
	
	public PacienteResponse toPacienteResponse(Paciente paciente) {
		return mapper.map(paciente, PacienteResponse.class);
	}

	public List<PacienteResponse> toPacienteResponseList(List<Paciente> pacientes){
		return pacientes.stream()
				.map(this::toPacienteResponse)
				.collect(Collectors.toList());
	}

}
