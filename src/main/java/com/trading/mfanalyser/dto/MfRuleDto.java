package com.trading.mfanalyser.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MfRuleDto {

	private long ruleId;

    private List<MfRuleFundDto> mfRuleFund = new ArrayList<>();

	private String ruleName;
	
	private String description;

	private String ruleType; //equity large cap
	
	private String isActive;
	
	private LocalDate createdDate;

	public long getRuleId() {
		return ruleId;
	}

	public void setRuleId(long ruleId) {
		this.ruleId = ruleId;
	}

	public List<MfRuleFundDto> getMfRuleFund() {
		return mfRuleFund;
	}

	public void setMfRuleFund(List<MfRuleFundDto> mfRuleFund) {
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
