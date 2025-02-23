package com.trading.mfanalyser.dto;

import java.util.List;

/**
 * https://www.baeldung.com/spring-data-jpa-projections
 */
public interface MfRuleInfo {
	long getRuleId();

	String getRuleName();

	String getDescription();

	String getRuleType();

	String getIsActive();

	List<MfRuleFundInfo> getMfRuleFund();
	
	// @Value("#{target.firstName + ' ' + target.lastName}")
	// String getFullName();
}
