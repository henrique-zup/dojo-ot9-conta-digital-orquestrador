package br.com.zup.orquestrador.pagamentoboleto;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import br.com.zup.orquestrador.contadigital.ContaDigital;

public class DadosPagamentoBoletoRequest {
	
	@Positive
	private BigDecimal valor;
	private String numeroBoleto;

	public DadosPagamentoBoletoRequest(BigDecimal valor, String numeroBoleto) {
		this.valor = valor;
		this.numeroBoleto = numeroBoleto;
	}

	public @Valid PagamentoBoletoRequest fromClient(ContaDigital conta) {
		return new PagamentoBoletoRequest(valor, numeroBoleto, conta);
	}

	public BigDecimal getValor() {
		return valor;
	}

	public String getNumeroBoleto() {
		return numeroBoleto;
	}
	
}
