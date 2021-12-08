package br.com.zup.orquestrador.contadigital;

import java.math.BigDecimal;

public class TransacaoRequest {

	private TipoDeTransacao tipoDeTransacao;
	private BigDecimal valor;

	private TransacaoRequest(TipoDeTransacao tipoDeTransacao, BigDecimal valor) {
		this.tipoDeTransacao = tipoDeTransacao;
		this.valor = valor;
	}

	public TipoDeTransacao getTipoDeTransacao() {
		return tipoDeTransacao;
	}

	public BigDecimal getValor() {
		return valor;
	}
	
	public static TransacaoRequest buildForDebito(BigDecimal valor) {
		return new TransacaoRequest(TipoDeTransacao.DEBITO, valor);
	}
	
	public static TransacaoRequest buildForCredito(BigDecimal valor) {
		return new TransacaoRequest(TipoDeTransacao.CREDITO, valor);
	}

}
