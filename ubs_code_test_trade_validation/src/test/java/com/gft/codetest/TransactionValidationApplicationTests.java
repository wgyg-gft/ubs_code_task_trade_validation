package com.gft.codetest;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.gft.codetest.entity.TransactionRecord;
import com.gft.codetest.service.RecordService;
import com.gft.codetest.service.ValidationService;
import com.gft.codetest.util.ISO4217Currency;
import com.gft.codetest.util.ServiceResponse;

@SpringBootTest
class TransactionValidationApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(TransactionValidationApplicationTests.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	RecordService recordService;

	@Autowired
	@Qualifier("FOREXTransactionValidationService")
	ValidationService validationService;
	
	@Autowired
	ISO4217Currency isoCurrencyList;

	@Test
	void testValidationService() throws ParseException {
		TransactionRecord tr = new TransactionRecord();
		tr.setCustomer("YODA1");
		tr.setCcyPair("EURUSB");
		tr.setType("Spot");
		tr.setDirection("BUY");
		tr.setTradeDate(sdf.parse("2021-08-19"));
		tr.setAmount1(new BigDecimal("1000000"));
		tr.setAmount2(new BigDecimal("1120000"));
		tr.setRate(new BigDecimal("1.2"));
		tr.setValueDate(sdf.parse("2021-08-15"));
		tr.setLegalEntity("UBS AG");
		tr.setTrader("JosefSchoenberger");
		
		ServiceResponse<TransactionRecord> sr = validationService.validateRecord(tr);

		logger.info("Test Result : {}", sr.getRemark());
		assertThat(sr.isSuccess()).isFalse();
		
		// test option
		tr = new TransactionRecord();
		tr.setCustomer("YODA1");
		tr.setCcyPair("EURUSD");
		tr.setType("VanillaOption");
		tr.setStyle("EUROPEAN");
		tr.setDirection("BUY");
		tr.setStrategy("CALL");
		tr.setTradeDate(sdf.parse("2021-08-19"));
		tr.setAmount1(new BigDecimal("1000000"));
		tr.setAmount2(new BigDecimal("1120000"));
		tr.setRate(new BigDecimal("1.2"));
		tr.setValueDate(sdf.parse("2021-08-12"));
		tr.setDeliveryDate(sdf.parse("2021-08-22"));
		tr.setExpiryDate(sdf.parse("2021-08-19"));
		tr.setPayCcy("USD");
		tr.setPremium(new BigDecimal("0.2"));
		tr.setPremiumCcy("USD");
		tr.setPremiumType("%USD");
		tr.setPremiumDate(sdf.parse("2020-08-12"));
		tr.setLegalEntity("UBS AG");
		tr.setTrader("JosefSchoenberger");
		
		sr = validationService.validateRecord(tr);

		logger.info("Test Result : {}", sr.getRemark());
		assertThat(sr.isSuccess()).isTrue();


		tr.setStyle("AMERICAN");
		sr = validationService.validateRecord(tr);
		logger.info("Test Result : {}", sr.getRemark());
		assertThat(sr.isSuccess()).isFalse();
	}
	
	@Test
	void testStringToTransactionRecords() {
		assertThat(recordService.readRecords("[]").isSuccess()).isEqualTo(true);

		ServiceResponse<Set<TransactionRecord>> sr = recordService.readRecords(
				"[{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"Spot\",\"direction\":\"BUY\",\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"valueDate\":\"2020-08-15\",\"legalEntity\":\"UBS AG\",\"trader\":\"JosefSchoenberger\"}]");
		
		assertThat(sr.getPayload().size()).isEqualTo(1);

		for (TransactionRecord transactionRecord : sr.getPayload()) {
			assertThat(transactionRecord.getCustomer()).isEqualTo("YODA1");
		}
	}
	
	@Test
	void readCurrencyList() {
		assertThat(isoCurrencyList.getAlphabeticCodeList().size()).isGreaterThan(0);
		assertThat(isoCurrencyList.getAlphabeticCodeList().contains("EUR")).isTrue();
	}
	
	@Test()
	void testFailCases() {
		assertThat(recordService.readRecords("").isSuccess()).isEqualTo(false);
		assertThat(recordService.readRecords("[").isSuccess()).isEqualTo(false);
	}
}
