package com.gft.codetest.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.gft.codetest.entity.TransactionRecord;
import com.gft.codetest.util.ServiceResponse;

@Service
public abstract class ValidationService {

	public abstract ServiceResponse<Set<TransactionRecord>> validateRecordSet(Set<TransactionRecord> recordSet);
	
	public abstract ServiceResponse<TransactionRecord> validateRecord(TransactionRecord record);

}
