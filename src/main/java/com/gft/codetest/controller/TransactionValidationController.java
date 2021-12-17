package com.gft.codetest.controller;

import java.util.Calendar;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gft.codetest.entity.TransactionRecord;
import com.gft.codetest.service.RecordService;
import com.gft.codetest.service.ValidationService;
import com.gft.codetest.util.ServiceResponse;

@RestController
public class TransactionValidationController {
	private static final Logger logger = LoggerFactory.getLogger(TransactionValidationController.class);

	@Autowired
	RecordService recordService;

	@Autowired
	@Qualifier("FOREXTransactionValidationService")
	ValidationService validationService;

	@PostMapping("/process")
	public ResponseEntity<Object> processJSONTransaction(@RequestBody String requestJSON) {
		logger.info("= Start processing JSON request size {}, start time {}", requestJSON.length(), Calendar.getInstance().getTimeInMillis());

		ServiceResponse<Set<TransactionRecord>> resultReadRecord = recordService.readRecords(requestJSON);

		if (!resultReadRecord.isSuccess()) {
			logger.info("= End processing JSON request, result : unable to parse json file, end time {}", Calendar.getInstance().getTimeInMillis());
			return ResponseEntity.badRequest().body("failed to read JSON input, error : " + resultReadRecord.getRemark());
		} else {
			ServiceResponse<Set<TransactionRecord>> resultValidateRecord = validationService.validateRecordSet(resultReadRecord.getPayload());

			if (!resultValidateRecord.isSuccess()) {
				logger.info("= End processing JSON request, result : failed, end time {}", Calendar.getInstance().getTimeInMillis());
				return ResponseEntity.badRequest().body(resultValidateRecord.getPayload());
			} else {
				logger.info("= End processing JSON request, result : {}, end time {}", resultValidateRecord.getRemark(), Calendar.getInstance().getTimeInMillis());
				return ResponseEntity.ok(resultValidateRecord.getPayload());
			}
		}

	}
}
