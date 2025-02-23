package com.trading.mfanalyser.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.trading.mfanalyser.dto.MfRuleInfo;
import com.trading.mfanalyser.entity.MfRule;

public interface MfRuleRepo extends CrudRepository<MfRule, Long> {

	List<MfRule> findByIsActive(String isActive);

	@Query("SELECT c.ruleId as ruleId,c.ruleName as ruleName,c.description as description,c.ruleType as ruleType,c.isActive as isActive,"
			+ "c.mfRuleFund as mfRuleFund FROM MfRule c WHERE c.isActive = ?1")
	List<MfRuleInfo> getAllActiveRuleWithFund(String isActive);
}
