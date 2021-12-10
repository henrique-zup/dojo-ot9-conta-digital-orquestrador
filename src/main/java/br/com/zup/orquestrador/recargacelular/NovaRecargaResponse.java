package br.com.zup.orquestrador.recargacelular;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NovaRecargaResponse {
	
	@JsonProperty
	private String numeroCelular;
	
	@JsonProperty
	private Operadora operadora;
	
	@JsonProperty
	private BigDecimal valor;
	
	@JsonProperty
	private String mensagem;

	public NovaRecargaBffResponse fromBFF() {
		return new NovaRecargaBffResponse(this.mensagem);
	}	
	public NovaRecargaResponse(String numeroCelular, Operadora operadora, BigDecimal valor, String mensagem) {
		this.numeroCelular = numeroCelular;
		this.operadora = operadora;
		this.valor = valor;
		this.mensagem = mensagem;
	}

}
