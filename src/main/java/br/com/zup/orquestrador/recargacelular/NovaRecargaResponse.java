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

}
