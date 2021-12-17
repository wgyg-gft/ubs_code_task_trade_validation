package com.gft.codetest.validator;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gft.codetest.entity.TransactionRecord;
import com.gft.codetest.util.ServiceResponse;

@Component
public class OptionTypeValidator extends TypeValidator {
	private static final Logger logger = LoggerFactory.getLogger(OptionTypeValidator.class);
	// the style can be either American or European
	// - American option style will have in addition the excerciseStartDate, which
	// has to be after the trade date but
	// before the expiry date
	// - expiry date and premium date shall be before delivery date

	public OptionTypeValidator() {
		super(Arrays.asList("VANILLAOPTION"));
	}

	@Override
	public ServiceResponse<String> validateTransaction(TransactionRecord tr) {
		logger.debug("Option type : validating transaction");

		boolean validated = true;
		StringBuilder remark = new StringBuilder();

		validated = validateStyle(tr.getStyle(), remark) && validated;

		if (tr.getStyle().equalsIgnoreCase("AMERICAN")) {
			validated = validated && validateFieldNotNull(tr.getExcerciseStartDate(), "excerciseStartDate", remark);
			validated = validated && validateFieldNotNull(tr.getTradeDate(), "tradeDate", remark);
			validated = validated && validateFieldNotNull(tr.getExpiryDate(), "expiryDate", remark);
			
			validated = validated && validateExeciseStartDateWithinTradeDateExpiryDate(tr.getExcerciseStartDate(), tr.getTradeDate(), tr.getExpiryDate(), remark);
		}

		validated = validated && validateFieldNotNull(tr.getExpiryDate(), "expiryDate", remark);
		validated = validated && validateFieldNotNull(tr.getPremiumDate(), "premiumDate", remark);
		validated = validated && validateFieldNotNull(tr.getDeliveryDate(), "deliveryDate", remark);
		
		validated = validated && validateExpiryDateAndPremiumDateBeforeDeliveryDate(tr.getExpiryDate(), tr.getPremiumDate(), tr.getDeliveryDate(), remark);

		
		if (validated) {
			return ServiceResponse.ok(remark.toString());
		} else {
			return ServiceResponse.error(remark.toString());
		}
	}

	private boolean validateExpiryDateAndPremiumDateBeforeDeliveryDate(Date expiryDate, Date premiumDate, Date deliveryDate, StringBuilder remark) {
		if (expiryDate.getTime() < deliveryDate.getTime() && premiumDate.getTime() < deliveryDate.getTime()) {
			return true;
		} else {
			remark.append(MessageFormat.format("Option : expiryDate {0} and premiumDate {1} shall be before deliveryDate {2}; ", dateFormat.format(expiryDate), dateFormat.format(premiumDate), dateFormat.format(deliveryDate)));
			return false;
		}
	}

	public boolean validateStyle(String style, StringBuilder remark) {
		if (style.equals("EUROPEAN") || style.equals("AMERICAN")) {
			return true;
		} else {
			remark.append("Option : Style need to be in EUROPEAN or AMERICAN; ");
			return false;
		}
	}

	public boolean validateExeciseStartDateWithinTradeDateExpiryDate(Date exerciseStartDate, Date tradeDate, Date expiryDate, StringBuilder remark) {
		if (exerciseStartDate.getTime() > tradeDate.getTime() && exerciseStartDate.getTime() < expiryDate.getTime()) {
			return true;
		} else {
			remark.append(MessageFormat.format("Option: exerciseStartDate {0} is not inside tradeDate {1} and expiryDate {2}; ", dateFormat.format(exerciseStartDate), dateFormat.format(tradeDate), dateFormat.format(expiryDate)));
			return false;
		}
	}
}
