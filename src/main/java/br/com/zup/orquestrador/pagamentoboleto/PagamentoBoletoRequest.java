package br.com.zup.orquestrador.pagamentoboleto;

import java.math.BigDecimal;

import br.com.zup.orquestrador.contadigital.ContaDigital;

public class PagamentoBoletoRequest {

	private BigDecimal valor;
	private String numeroBoleto;
	private ContaDigital conta;

	public PagamentoBoletoRequest(BigDecimal valor, String numeroBoleto, ContaDigital conta) {
		this.valor = valor;
		this.numeroBoleto = numeroBoleto;
		this.conta = conta;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public String getNumeroBoleto() {
		return numeroBoleto;
	}

	public ContaDigital getConta() {
		return conta;
	}

}
