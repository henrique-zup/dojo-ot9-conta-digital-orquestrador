package br.com.zup.orquestrador.contadigital;

public class TransacaoResponse {

	private String idUsuario;
	private String numeroConta;
	private String email;

	public String getIdUsuario() {
		return idUsuario;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public String getEmail() {
		return email;
	}

	public ContaDigital fromModel() {
		return new ContaDigital(idUsuario, numeroConta, email);
	}

}
