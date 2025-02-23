package com.trading.mfanalyser.dto;
/**
 * https://www.baeldung.com/spring-data-jpa-projections
 */
public interface MfRuleFundInfo {

	String getIsinCode();
	String getFundName();
	String getFundCategory();
	String getFundSubCategory();
	int getMonthId();
	// @Value("#{target.firstName + ' ' + target.lastName}")
	// String getFullName();
	
}
