package com.gft.codetest.validator;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gft.codetest.entity.TransactionRecord;
import com.gft.codetest.util.ISO4217Currency;
import com.gft.codetest.util.ServiceResponse;

@Component
public class AllTypeValidator extends TypeValidator {
	private static final Logger logger = LoggerFactory.getLogger(AllTypeValidator.class);

	public AllTypeValidator() {
		super(Arrays.asList("ALL"));
	}

	@Autowired
	ISO4217Currency isoCurrency;

	@Override
	public ServiceResponse<String> validateTransaction(TransactionRecord tr) {
		logger.debug("ALL : validating transaction");

		boolean validated = true;
		StringBuilder remark = new StringBuilder();

		validated = validated && validateFieldNotNull(tr.getValueDate(), "valueDate", remark);
		validated = validated && validateFieldNotNull(tr.getTradeDate(), "tradeDate", remark);

		validated = validated && validateIsValueDateBehindTradeDate(tr.getValueDate(), tr.getTradeDate(), remark);

		validated = validated && validateIsDateInWeekend(tr.getValueDate(), "valueDate", remark);
		validated = validated && validateIsDateInWeekend(tr.getTradeDate(), "tradeDate", remark);

		validated = validateCurrency(tr.getBaseCurrency(), remark) && validated;
		validated = validateCurrency(tr.getQuoteCurrency(), remark) && validated;

		if (validated) {
			return ServiceResponse.ok(remark.toString());
		} else {
			return ServiceResponse.error(remark.toString());
		}
	}

	public boolean validateIsValueDateBehindTradeDate(Date valueDate, Date tradeDate, StringBuilder remark) {
		if (valueDate.getTime() < tradeDate.getTime()) {
			return true;
		} else {
			remark.append(MessageFormat.format("valueDate {0} cannot be before tradeDate {1}; ", dateFormat.format(valueDate), dateFormat.format(tradeDate)));
			return false;
		}
	}

	public boolean validateIsDateInWeekend(Date date, String dateType, StringBuilder remark) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			return true;
		} else {
			remark.append(MessageFormat.format("Date {0} {1} is not in the weekend; ", dateType, dateFormatWithDOW.format(date)));
			return false;
		}
	}

	public boolean validateCurrency(String currency, StringBuilder remark) {
		if (isoCurrency.getAlphabeticCodeList().contains(currency)) {
			return true;
		} else {
			remark.append(MessageFormat.format("Currency {0} is not in ISO 4217 Currency List; ", currency));
			return false;
		}
	}
}
