package br.com.fiap.mspayment.entity.enums;

public enum StatusType {
	OPEN("ABERTO"),
	PAID("PAGO");

	private final String value;

	StatusType(String value) {
		this.value = value;
	}

	public String getValue(){
		return this.value;
	}
}
