package com.trading.mfanalyser.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "T_MF_RULE_FUND_HOLDING_H", schema = "APP")
public class MfRuleFundHoldingHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "HISTORY_ID")
	private long historyId;
	
	@Column(name = "HOLDING_ID")
	private long holdingId;
	
    @Column(name = "fund_id")
	private long fundId;

	@Column(name = "RULE_ID")
	private long ruleId;
	
	@Column(name="STOCK_NAME")
	private String stockName;

	@Column(name="AMFI_CODE")
	private String amfiCode;

	@Column(name="ISIN_CODE")
	private String isinCode;

	@Column(name="SECTOR")
	private String sector;

	@Column(name="GLOBAL_INDUSTRY")
	private String globalIndustry;

	@Column(name="HOLDING_PERCENTAGE")
	private double holdingPercentage;

	@Column(name="HOLDING_DATE", columnDefinition = "DATE")
	@CreationTimestamp
	private LocalDate holdingDate;

	public long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(long historyId) {
		this.historyId = historyId;
	}

	public long getHoldingId() {
		return holdingId;
	}

	public void setHoldingId(long holdingId) {
		this.holdingId = holdingId;
	}

	public long getFundId() {
		return fundId;
	}

	public void setFundId(long fundId) {
		this.fundId = fundId;
	}

	public long getRuleId() {
		return ruleId;
	}

	public void setRuleId(long ruleId) {
		this.ruleId = ruleId;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getAmfiCode() {
		return amfiCode;
	}

	public void setAmfiCode(String amfiCode) {
		this.amfiCode = amfiCode;
	}

	public String getIsinCode() {
		return isinCode;
	}

	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getGlobalIndustry() {
		return globalIndustry;
	}

	public void setGlobalIndustry(String globalIndustry) {
		this.globalIndustry = globalIndustry;
	}

	public double getHoldingPercentage() {
		return holdingPercentage;
	}

	public void setHoldingPercentage(double holdingPercentage) {
		this.holdingPercentage = holdingPercentage;
	}

	public LocalDate getHoldingDate() {
		return holdingDate;
	}

	public void setHoldingDate(LocalDate holdingDate) {
		this.holdingDate = holdingDate;
	}

	
}
