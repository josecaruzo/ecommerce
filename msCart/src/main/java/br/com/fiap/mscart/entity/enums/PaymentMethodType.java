package br.com.fiap.mscart.entity.enums;

public enum PaymentMethodType {
	BANK_SLIP("BOLETO BANCÁRIO"),
	PIX("PIX"),
	CREDIT("CARTÃO DE CRÉDITO"),
	DEBIT("CARTÃO DE DÉBITO");

	private final String value;

	PaymentMethodType(String value) {
		this.value = value;
	}

	public String getValue(){
		return this.value;
	}
}
