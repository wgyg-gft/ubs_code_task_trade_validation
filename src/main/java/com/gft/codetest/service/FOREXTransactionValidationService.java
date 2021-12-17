package com.gft.codetest.service;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

import com.gft.codetest.entity.TransactionRecord;
import com.gft.codetest.util.ServiceResponse;
import com.gft.codetest.validator.AllTypeValidator;
import com.gft.codetest.validator.OptionTypeValidator;
import com.gft.codetest.validator.SpotForwardTypeValidator;
import com.gft.codetest.validator.TypeValidator;

@Service("FOREXTransactionValidationService")
public class FOREXTransactionValidationService extends ValidationService {
	private static final Logger logger = LoggerFactory.getLogger(FOREXTransactionValidationService.class);

	@Value("${valid.customers}")
	List<String> validCustomers;
	
	@Autowired
	private AutowireCapableBeanFactory autowireCapableBeanFactory;

	List<TypeValidator> typeValidator = Arrays.asList(
			new AllTypeValidator(), 
			new SpotForwardTypeValidator(), 
			new OptionTypeValidator());

	@Override
	public ServiceResponse<Set<TransactionRecord>> validateRecordSet(Set<TransactionRecord> recordSet) {

		int numberOfRecords = 0;
		int numberOfFailRecords = 0;
		int numberOfIgnoreRecords = 0;
		int numberOfSuccessRecords = 0;

		for (TransactionRecord transactionRecord : recordSet) {
			numberOfRecords++;
			ServiceResponse<TransactionRecord> validateResult = validateRecord(transactionRecord);
			
			if(!validateResult.isSuccess()) {
				if(validateResult.getException() != null 
						&& validateResult.getException().getMessage().equals("non-process-transaction-record")) {
					// ignore this record if this is a non supported customer
					numberOfIgnoreRecords++;
					transactionRecord.setValidate(null);
				}else {
					numberOfFailRecords++;
					transactionRecord.setValidate(false);
					transactionRecord.setRemark(validateResult.getRemark());
				}
			} else {
				numberOfSuccessRecords++;
				transactionRecord.setValidate(true);
			}
		}
		
		return ServiceResponse.ok(recordSet, MessageFormat.format("success {0} / fail {1} / ignore {2} / total {3} records have been processed", numberOfSuccessRecords, numberOfFailRecords, numberOfIgnoreRecords, numberOfRecords));
	}

	@Override
	public ServiceResponse<TransactionRecord> validateRecord(TransactionRecord tr) {
		logger.info("- Start validate transaction record : Customer {}, Amount1 {}, Amount2 {}, ccyPair {}, Type {}", tr.getCustomer(), tr.getAmount1(), tr.getAmount2(), tr.getCcyPair(), tr.getType());
		boolean isAllValidated = true;
		StringBuilder remarks = new StringBuilder();
		
		// check is supported customer
		if (!validCustomers.stream().anyMatch(tr.getCustomer()::equalsIgnoreCase)) {
			logger.info("- End validate transaction record, result ignore");
			return ServiceResponse.error(new Exception("non-process-transaction-record"), "non-process-transaction-record");
		}
		
		for (TypeValidator validator : typeValidator) {
			autowireCapableBeanFactory.autowireBean(validator);

			// using SpotFowardTypeValidator to validate type contains "Spot" or "Forward"
			if (
			validator.getTypeList().stream().anyMatch(tr.getType()::equalsIgnoreCase) || 
			validator.getTypeList().stream().anyMatch("ALL"::equalsIgnoreCase)) {

				ServiceResponse<String> sr = validator.validateTransaction(tr);

				if (!sr.isSuccess()) {
					remarks.append(sr.getRemark());
				}

				isAllValidated = isAllValidated && sr.isSuccess();
				logger.info("{} validator return {}, remark {}", validator.getTypeList(), sr.isSuccess(), sr.getRemark());
			}
		}
		
		if (isAllValidated) {
			logger.info("- End validate transaction record, result success");
			return ServiceResponse.ok(tr);
		} else {
			logger.info("- End validate transaction record, result fail, remark : {}", remarks.toString());
			return ServiceResponse.error(remarks.toString());
		}
	}
}
