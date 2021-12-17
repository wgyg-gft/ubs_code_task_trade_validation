package com.gft.codetest.util;

import com.opencsv.bean.CsvBindByName;

public class ISO4217CurrencyNode {
	// Entity Currency AlphabeticCode NumericCode MinorUnit WithdrawalDate

	@CsvBindByName
	private String Entity;

	@CsvBindByName
	private String Currency;

	@CsvBindByName
	private String AlphabeticCode;

	@CsvBindByName
	private String NumericCode;

	@CsvBindByName
	private String MinorUnit;

	@CsvBindByName
	private String WithdrawalDate;

	public String getEntity() {
		return Entity;
	}

	public void setEntity(String entity) {
		Entity = entity;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public String getAlphabeticCode() {
		return AlphabeticCode;
	}

	public void setAlphabeticCode(String alphabeticCode) {
		AlphabeticCode = alphabeticCode;
	}

	public String getNumericCode() {
		return NumericCode;
	}

	public void setNumericCode(String numericCode) {
		NumericCode = numericCode;
	}

	public String getMinorUnit() {
		return MinorUnit;
	}

	public void setMinorUnit(String minorUnit) {
		MinorUnit = minorUnit;
	}

	public String getWithdrawalDate() {
		return WithdrawalDate;
	}

	public void setWithdrawalDate(String withdrawalDate) {
		WithdrawalDate = withdrawalDate;
	}

	@Override
	public String toString() {
		return "CurrencyNameNode [Entity=" + Entity + ", Currency=" + Currency + ", AlphabeticCode=" + AlphabeticCode
				+ ", NumericCode=" + NumericCode + ", MinorUnit=" + MinorUnit + ", WithdrawalDate=" + WithdrawalDate
				+ "]";
	}

}
