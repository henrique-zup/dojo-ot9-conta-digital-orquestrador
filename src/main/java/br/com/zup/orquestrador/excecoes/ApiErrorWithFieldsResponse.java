package br.com.zup.orquestrador.excecoes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiErrorWithFieldsResponse extends ApiErrorResponse {
	
	@JsonProperty
	private List<Map<String, String>> campos = new ArrayList<Map<String,String>>();

	public ApiErrorWithFieldsResponse(List<FieldError> fieldErrors) {
		super("Argumentos inválidos.");
		fieldErrors.forEach(fe -> {
			adicionaCampo(fe.getField(), fe.getDefaultMessage());
		});
		
	}

	private void adicionaCampo(String field, String message) {
		campos.add(Map.of(field, message));
	}

}
