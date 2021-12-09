package br.com.zup.orquestrador.excecoes;

public class ApiErrorResponse {

	protected String resposta;

	public ApiErrorResponse(String resposta) {
		this.resposta = resposta;
	}

	public String getResposta() {
		return resposta;
	}

}
