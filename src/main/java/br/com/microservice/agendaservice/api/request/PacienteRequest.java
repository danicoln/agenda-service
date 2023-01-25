package br.com.microservice.agendaservice.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacienteRequest {

	@NotBlank(message = "Nome do paciente é obrigatório!")
	private String nome;
	@NotBlank(message = "Sobrenome do paciente é obrigatório!")
	private String sobrenome;
	@NotBlank(message = "CPF do paciente é obrigatório!")
	private String cpf;
	private String email;

}
