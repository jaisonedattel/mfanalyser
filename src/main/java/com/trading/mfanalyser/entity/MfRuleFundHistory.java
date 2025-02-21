package com.trading.mfanalyser.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "T_MF_RULE_FUND_H", schema = "APP")
public class MfRuleFundHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "HISTORY_ID")
	private long historyId;

	@Column(name = "FUND_ID")
	private long fundId;

	@Column(name = "RULE_ID")
	private long ruleId;

	@Column(name = "ISIN_CODE", length=150)
	private String isinCode;
	
	@Column(name = "FUND_NAME", length=250)
	private String fundName;

	@Column(name = "FUND_CATEGORY", length=100)
	private String fundCategory; //equity , debt
	
	@Column(name = "FUND_SUB_CATEGORY", length=100)
	private String fundSubCategory; //equity , debt
	
	@Column(name = "MONTH_ID")
	private int monthId; //202501
	
	@Column(name = "CREATED_DATE", columnDefinition = "DATE")
	@CreationTimestamp
	private LocalDate createdDate;

	public long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(long historyId) {
		this.historyId = historyId;
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

	public String getIsinCode() {
		return isinCode;
	}

	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getFundCategory() {
		return fundCategory;
	}

	public void setFundCategory(String fundCategory) {
		this.fundCategory = fundCategory;
	}

	public String getFundSubCategory() {
		return fundSubCategory;
	}

	public void setFundSubCategory(String fundSubCategory) {
		this.fundSubCategory = fundSubCategory;
	}

	public int getMonthId() {
		return monthId;
	}

	public void setMonthId(int monthId) {
		this.monthId = monthId;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	
}
