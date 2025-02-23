package com.trading.mfanalyser.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "T_MF_RULE_MASTER", schema = "APP")
public class MfRule {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RULE_ID")
	private long ruleId;

	@OneToMany(mappedBy = "mfRule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MfRuleFund> mfRuleFund = new ArrayList<>();

	@Column(name = "RULE_NAME", length=150)
	private String ruleName;
	
	@Column(name = "DESCRIPTION", length=250)
	private String description;

	@Column(name = "RULE_TYPE", length=100)
	private String ruleType; //equity large cap

	@Column(name = "DATA_API_URL", length=250)
	private String dataApiUrl;

	@Column(name = "IS_ACTIVE", columnDefinition = "varchar(10) default 'Y'" )
	private String isActive;
	
	@Column(name = "CREATED_DATE", columnDefinition = "DATE")
	@CreationTimestamp
	private LocalDate createdDate;

	public long getRuleId() {
		return ruleId;
	}

	public void setRuleId(long ruleId) {
		this.ruleId = ruleId;
	}

	public List<MfRuleFund> getMfRuleFund() {
		return mfRuleFund;
	}

	public void setMfRuleFund(List<MfRuleFund> mfRuleFund) {
		this.mfRuleFund = mfRuleFund;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public String getDataApiUrl() {
		return dataApiUrl;
	}

	public void setDataApiUrl(String dataApiUrl) {
		this.dataApiUrl = dataApiUrl;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "MfRule [ruleId=" + ruleId + ", ruleName=" + ruleName + ", description=" + description + "]";
	}

}
