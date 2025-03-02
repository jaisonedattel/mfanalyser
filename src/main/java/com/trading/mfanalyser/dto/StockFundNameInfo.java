package com.trading.mfanalyser.dto;

/**
 * https://www.baeldung.com/spring-data-jpa-projections
 */
public interface StockFundNameInfo {

	String getHoldingPercentage();
	String getFundName();
}
