package br.com.zup.orquestrador.pagamentoboleto;

import java.math.BigDecimal;

import javax.validation.Valid;

import br.com.zup.orquestrador.contadigital.ContaDigital;

public class DadosPagamentoBoletoRequest {
	
	private Long idUsuario;
	private BigDecimal valor;
	private String numeroBoleto;

	public @Valid PagamentoBoletoRequest fromClient(ContaDigital conta) {
		return new PagamentoBoletoRequest(idUsuario, valor, numeroBoleto, conta);
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public String getNumeroBoleto() {
		return numeroBoleto;
	}
	
}
