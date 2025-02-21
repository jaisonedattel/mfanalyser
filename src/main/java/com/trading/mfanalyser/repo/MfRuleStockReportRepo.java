package com.trading.mfanalyser.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.trading.mfanalyser.entity.MfStockReportEntity;

public interface MfRuleStockReportRepo  extends CrudRepository<MfStockReportEntity,Long> {

	public List<MfStockReportEntity> findByRuleId(long ruleId);
	
	@Query("select count(u) from MfStockReportEntity u WHERE u.ruleId = ?1 and u.lastRunDate= ?2")
	long getCountOfReportForDate(long ruleId, LocalDate date);
}
