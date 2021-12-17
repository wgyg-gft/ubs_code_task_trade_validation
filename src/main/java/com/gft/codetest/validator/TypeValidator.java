package com.gft.codetest.validator;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Component;

import com.gft.codetest.entity.TransactionRecord;
import com.gft.codetest.util.ServiceResponse;

@Component
public abstract class TypeValidator {
	protected SimpleDateFormat dateFormatWithDOW = new SimpleDateFormat("yyyy-MM-dd EEEE");
	protected SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private List<String> typeList;

	public TypeValidator() {
	}

	public TypeValidator(List<String> typeList) {
		this.typeList = typeList;
	}

	public List<String> getTypeList() {
		return typeList;
	}

	public void setType(List<String> type) {
		this.typeList = type;
	}

	public abstract ServiceResponse<String> validateTransaction(TransactionRecord transactionRecord);

	public boolean validateFieldNotNull(Object value, String field, StringBuilder remark) {
		if (value == null) {
			remark.append(MessageFormat.format("{0} cannot be empty; ", field));
			return false;
		} else {
			return true;
		}
	}

	public boolean validateFieldIn(String item, List<String> validList, StringBuilder remark) {
		if (!validList.stream().anyMatch(item::equalsIgnoreCase)) {
			remark.append(MessageFormat.format("{0} not in {1}; ", item, validList));
			return false;
		} else {
			return true;
		}
	}
}
