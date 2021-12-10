package br.com.zup.orquestrador.excecoes.clients;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.zup.orquestrador.excecoes.ApiErrorResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecargaCelularErrorResponse {

	private String erro;

	public String getErro() {
		return erro;
	}

	public ApiErrorResponse toApiErrorResponse() {
		return new ApiErrorResponse(this.erro);
	}
}
