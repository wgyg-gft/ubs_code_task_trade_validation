package com.gft.codetest.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

public class ISO4217Currency {
	List<ISO4217CurrencyNode> currencyList;

	public ISO4217Currency() {
		try {
			InputStream is = new ClassPathResource("codes-all_csv.csv").getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			HeaderColumnNameMappingStrategy<ISO4217CurrencyNode> strategy = new HeaderColumnNameMappingStrategy<>();
			strategy.setType(ISO4217CurrencyNode.class);

			CsvToBean<ISO4217CurrencyNode> csvToBean = new CsvToBeanBuilder<ISO4217CurrencyNode>(br)
					.withMappingStrategy(strategy).withIgnoreLeadingWhiteSpace(true).build();

			currencyList = csvToBean.parse();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<ISO4217CurrencyNode> getCurrencyList() {
		return currencyList;
	}

	public List<String> getAlphabeticCodeList() {
		return currencyList.stream()
				.map(ISO4217CurrencyNode::getAlphabeticCode)
				.filter(code -> !code.isEmpty())
				.collect(Collectors.toList());
	}
}
