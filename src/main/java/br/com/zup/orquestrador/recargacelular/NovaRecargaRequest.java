package br.com.zup.orquestrador.recargacelular;

import java.math.BigDecimal;

import javax.validation.constraints.*;

public class NovaRecargaRequest {

	@NotBlank
	@Pattern(regexp = "^\\(?[1-9]{2}\\)?[9]{0,1}[6-9]{1}[0-9]{7}$", message = "Formato inválido.")
	private String numeroCelular;

	@NotBlank
	private String operadora;

	@PositiveOrZero
	@NotNull
	private BigDecimal valor;

	public NovaRecargaRequest(String numeroCelular, String operadora, BigDecimal valor) {
		this.numeroCelular = numeroCelular;
		this.operadora = operadora;
		this.valor = valor;
	}

	public String getNumeroCelular() {
		return numeroCelular;
	}

	public String getOperadora() {
		return operadora;
	}

	public BigDecimal getValor() {
		return valor;
	}
}
