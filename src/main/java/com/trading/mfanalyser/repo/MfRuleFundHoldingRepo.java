package com.trading.mfanalyser.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.trading.mfanalyser.dto.StockFundNameInfo;
import com.trading.mfanalyser.entity.MfRuleFundHolding;

public interface MfRuleFundHoldingRepo  extends CrudRepository<MfRuleFundHolding,Long> {

	@Modifying
	@Query("delete MfRuleFundHolding u WHERE u.ruleId = ?1")
	void deleteByRuleId(long ruleId);
	
	
	@Query("select count(u) from MfRuleFundHolding u WHERE u.ruleId = ?1 and holdingDate= ?2 ")
	long getCountOfFundHoldingForDate(long ruleId, LocalDate date);

	
	//select distinct a.stock_name, b.fund_name from 
	//T_MF_RULE_FUND_HOLDING a join T_MF_RULE_FUND b on (a.fund_id=b.fund_id)
	//where stock_name='HDFC Bank Ltd' and a.rule_id=14952;
	@Query("select distinct b.fundName as fundName, a.holdingPercentage as holdingPercentage from "
			+ " MfRuleFundHolding a JOIN MfRuleFund b on (a.mfRuleFund.id=b.fundId) where a.ruleId=?1 and a.stockName = ?2")
	public List<StockFundNameInfo> getStockHoldingFundNames(long ruleId, String stockName);
}
