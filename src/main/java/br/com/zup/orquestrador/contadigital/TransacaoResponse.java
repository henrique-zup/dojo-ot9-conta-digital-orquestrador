package br.com.zup.orquestrador.contadigital;

public class TransacaoResponse {

	private String idCliente;
	private String numeroConta;
	private String email;

	public String getIdCliente() {
		return idCliente;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public String getEmail() {
		return email;
	}

	public ContaDigital fromModel() {
		return new ContaDigital(idCliente, numeroConta, email);
	}

}
