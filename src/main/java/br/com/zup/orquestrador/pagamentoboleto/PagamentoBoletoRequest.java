package br.com.zup.orquestrador.pagamentoboleto;

import java.math.BigDecimal;

import br.com.zup.orquestrador.contadigital.ContaDigital;

public class PagamentoBoletoRequest {

	private Long idUsuario;
	private BigDecimal valor;
	private String numeroBoleto;
	private ContaDigital conta;

	public PagamentoBoletoRequest(Long idUsuario, BigDecimal valor, String numeroBoleto, ContaDigital conta) {
		this.idUsuario = idUsuario;
		this.valor = valor;
		this.numeroBoleto = numeroBoleto;
		this.conta = conta;
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

	public ContaDigital getConta() {
		return conta;
	}

}
