package com.gft.codetest.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class TransactionRecord {
	private String customer;
	private String ccyPair;
	private String type;
	private String direction;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date tradeDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date valueDate; // not in option

	private BigDecimal amount1;
	private BigDecimal amount2;

	private BigDecimal rate;

	private String legalEntity;
	private String trader;

	// fields for option
    @JsonInclude(Include.NON_NULL)
	private String style;
    @JsonInclude(Include.NON_NULL)
	private String strategy;

    @JsonInclude(Include.NON_NULL)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date deliveryDate;
    @JsonInclude(Include.NON_NULL)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date expiryDate;
    @JsonInclude(Include.NON_NULL)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date excerciseStartDate;

    @JsonInclude(Include.NON_NULL)
	private String payCcy;

    @JsonInclude(Include.NON_NULL)
	private BigDecimal premium;
    @JsonInclude(Include.NON_NULL)
	private String premiumCcy;
    @JsonInclude(Include.NON_NULL)
	private String premiumType;
    @JsonInclude(Include.NON_NULL)
	private Date premiumDate;

    @JsonInclude(Include.NON_NULL)
	private String remark;
    @JsonInclude(Include.NON_NULL)
	private Boolean isValidate;

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getCcyPair() {
		return ccyPair;
	}

	public void setCcyPair(String ccyPair) {
		this.ccyPair = ccyPair;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public BigDecimal getAmount1() {
		return amount1;
	}

	public void setAmount1(BigDecimal amount1) {
		this.amount1 = amount1;
	}

	public BigDecimal getAmount2() {
		return amount2;
	}

	public void setAmount2(BigDecimal amount2) {
		this.amount2 = amount2;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}

	public String getTrader() {
		return trader;
	}

	public void setTrader(String trader) {
		this.trader = trader;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getPayCcy() {
		return payCcy;
	}

	public void setPayCcy(String payCcy) {
		this.payCcy = payCcy;
	}

	public BigDecimal getPremium() {
		return premium;
	}

	public void setPremium(BigDecimal premium) {
		this.premium = premium;
	}

	public String getPremiumCcy() {
		return premiumCcy;
	}

	public void setPremiumCcy(String premiumCcy) {
		this.premiumCcy = premiumCcy;
	}

	public Date getPremiumDate() {
		return premiumDate;
	}

	public void setPremiumDate(Date premiumDate) {
		this.premiumDate = premiumDate;
	}

	@JsonIgnore
	public String getBaseCurrency() {
		return this.getCcyPair().substring(0, 3);
	}

	@JsonIgnore
	public String getQuoteCurrency() {
		return this.getCcyPair().substring(3, 6);
	}

	public Date getExcerciseStartDate() {
		return excerciseStartDate;
	}

	public void setExcerciseStartDate(Date excerciseStartDate) {
		this.excerciseStartDate = excerciseStartDate;
	}

	public String getPremiumType() {
		return premiumType;
	}

	public void setPremiumType(String premiumType) {
		this.premiumType = premiumType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    @JsonInclude(Include.NON_NULL)
	public Boolean isValidate() {
		return isValidate;
	}

	public void setValidate(Boolean isValidate) {
		this.isValidate = isValidate;
	}

}
