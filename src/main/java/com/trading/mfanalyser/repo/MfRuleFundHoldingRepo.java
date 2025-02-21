package com.trading.mfanalyser.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.trading.mfanalyser.entity.MfRuleFundHolding;

public interface MfRuleFundHoldingRepo  extends CrudRepository<MfRuleFundHolding,Long> {

	@Modifying
	@Query("delete MfRuleFundHolding u WHERE u.ruleId = ?1")
	void deleteByRuleId(long ruleId);
	
	
}
