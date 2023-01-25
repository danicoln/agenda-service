package br.com.microservice.agendaservice.api.mapper;

import br.com.microservice.agendaservice.api.request.PacienteRequest;
import br.com.microservice.agendaservice.api.response.PacienteResponse;
import br.com.microservice.agendaservice.domain.entity.Paciente;

public class PacienteMapper {

	public static Paciente toPaciente(PacienteRequest request) {
		Paciente paciente = new Paciente();
		paciente.setNome(request.getNome());
		paciente.setSobrenome(request.getSobrenome());
		paciente.setEmail(request.getEmail());
		paciente.setCpf(request.getCpf());
		return paciente;
	}

	public static PacienteResponse toPacienteResponse(Paciente paciente) {
		PacienteResponse response = new PacienteResponse();
		response.setId(paciente.getId());
		response.setNome(paciente.getNome());
		response.setSobrenome(paciente.getSobrenome());
		response.setEmail(paciente.getEmail());
		response.setCpf(paciente.getCpf());
		return response;

	}

}
