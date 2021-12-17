package com.gft.codetest.service;

import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.codetest.entity.TransactionRecord;
import com.gft.codetest.util.ServiceResponse;

@Service
public class RecordService {
	private static final Logger logger = LoggerFactory.getLogger(RecordService.class);

	@Autowired
	ObjectMapper om;

	public ServiceResponse<Set<TransactionRecord>> readRecords(String input) {
		logger.debug("Processing input {}", input);
		try {
			if (input != null && !input.equals("")) {
				logger.debug("JSON format result {}", om.writeValueAsString(om.readValue(input, TransactionRecord[].class)));
				return ServiceResponse.ok(Set.of(om.readValue(input, TransactionRecord[].class)));
			} else {
				return ServiceResponse.error("input is empty");
			}
		} catch (JsonProcessingException e) {
			return ServiceResponse.error(e, ExceptionUtils.getMessage(e));
		}
	}
}
