package br.com.pratica.spring.praticaspring.error;

public class CustomErrorType {
	
	private String erroMessage;

	public CustomErrorType(String erroMessage) {
		this.erroMessage = erroMessage;
	}

	public String getErroMessage() {
		return erroMessage;
	}
}
