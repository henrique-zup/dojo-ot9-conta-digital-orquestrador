package br.com.zup.orquestrador.contadigital;

import java.math.BigDecimal;

public class TransacaoRequest {

	private TipoDeTransacao tipoTransacao;
	private BigDecimal valor;

	private TransacaoRequest(TipoDeTransacao tipoDeTransacao, BigDecimal valor) {
		this.tipoTransacao = tipoDeTransacao;
		this.valor = valor;
	}

	public TipoDeTransacao getTipoTransacao() {
		return tipoTransacao;
	}

	public BigDecimal getValor() {
		return valor;
	}
	
	public static TransacaoRequest buildForDebito(BigDecimal valor) {
		return new TransacaoRequest(TipoDeTransacao.DEBITAR, valor);
	}
	
	public static TransacaoRequest buildForCredito(BigDecimal valor) {
		return new TransacaoRequest(TipoDeTransacao.CREDITAR, valor);
	}

}
