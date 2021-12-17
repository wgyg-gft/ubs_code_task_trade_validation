package com.gft.codetest.validator;

import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gft.codetest.entity.TransactionRecord;
import com.gft.codetest.util.ServiceResponse;

@Component
public class SpotForwardTypeValidator extends TypeValidator {
	private static final Logger logger = LoggerFactory.getLogger(SpotForwardTypeValidator.class);

	public SpotForwardTypeValidator() {
		super(Arrays.asList("SPOT", "FORWARD"));
	}

	@Override
	public ServiceResponse<String> validateTransaction(TransactionRecord tr) {
		logger.debug("Spot/Foward type : validating transaction");

		boolean validated = true;
		StringBuilder remark = new StringBuilder();

		//validated = validateValueDateByProductType(tr.getValueDate(), remark) && validated;

		if (validated) {
			return ServiceResponse.ok(remark.toString());
		} else {
			return ServiceResponse.error(remark.toString());
		}
	}

	public boolean validateValueDateByProductType(Date valueDate, StringBuilder remark) {
		remark.append("fail on validating valueDate; ");
		return false;
	}
}
