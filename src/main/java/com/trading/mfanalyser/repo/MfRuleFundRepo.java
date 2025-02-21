package com.trading.mfanalyser.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.trading.mfanalyser.entity.MfRuleFund;

public interface MfRuleFundRepo  extends CrudRepository<MfRuleFund,Long> {

	/**
	 * This will not apply cascade and create fk error... so remove the child first
	 * 
	 * @param ruleId
	 */
	@Modifying
	@Query("delete MfRuleFund u WHERE u.mfRule.id = ?1")
	void deleteByRuleId(long ruleId);
	
	
	
	/**
	 * To delete a child entity with FetchType.LAZY, you need to:
		First, fetch the child entity explicitly using a separate query. 
		Then, remove the child entity from the parent's collection 
		and call delete on the child entity.
		OR If you are in transactional block before the end remove the child from parent object manually
	 * 
	 * @param ruleId
	 * @return
	 */
	@Query("select u from MfRuleFund u WHERE u.mfRule.id = ?1")
	List<MfRuleFund> getAllFundByRule(long ruleId);
	
	//cast(u.createdDate as LocalDate)
	@Query("select count(u) from MfRuleFund u WHERE u.mfRule.id = ?1 and cast(u.createdDate as LocalDate)= cast(?2 as LocalDate)")
	long getCountOfFundForDate(long ruleId, LocalDateTime date);

}
