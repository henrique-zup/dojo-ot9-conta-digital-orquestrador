package br.com.zup.orquestrador.contadigital;

public class ContaDigital {

	private String idUsuario;
	private String numeroConta;
	private String email;
	
	public ContaDigital(String idUsuario, String numeroConta, String email) {
		this.idUsuario = idUsuario;
		this.numeroConta = numeroConta;
		this.email = email;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "ContaDigital [idUsuario=" + idUsuario + ", numeroConta=" + numeroConta + ", email=" + email + "]";
	}
	
}
