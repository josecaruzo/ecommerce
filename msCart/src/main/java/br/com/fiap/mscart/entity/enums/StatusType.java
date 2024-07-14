package br.com.fiap.mscart.entity.enums;

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
