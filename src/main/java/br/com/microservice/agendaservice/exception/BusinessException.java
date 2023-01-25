package br.com.microservice.agendaservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// retorna um codigo 422;
@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public BusinessException(String msg) {
		super(msg);
	}
}
