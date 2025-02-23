package com.trading.mfanalyser.dto;

import java.time.LocalDate;

import jakarta.persistence.Column;

public class MfRuleFundDto {

	private long fundId;

	private String isinCode;

	private String fundName;

	private String fundCategory; // equity , debt

	private String fundSubCategory; // equity , debt

	private double fundReturn1Yr;
	
	private double fundReturn3Yr;
	
	private double fundReturn5Yr;

	private int monthId; // 202501

	private LocalDate createdDate;

	public long getFundId() {
		return fundId;
	}

	public void setFundId(long fundId) {
		this.fundId = fundId;
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

	public double getFundReturn1Yr() {
		return fundReturn1Yr;
	}

	public void setFundReturn1Yr(double fundReturn1Yr) {
		this.fundReturn1Yr = fundReturn1Yr;
	}

	public double getFundReturn3Yr() {
		return fundReturn3Yr;
	}

	public void setFundReturn3Yr(double fundReturn3Yr) {
		this.fundReturn3Yr = fundReturn3Yr;
	}

	public double getFundReturn5Yr() {
		return fundReturn5Yr;
	}

	public void setFundReturn5Yr(double fundReturn5Yr) {
		this.fundReturn5Yr = fundReturn5Yr;
	}

	@Override
	public String toString() {
		return "MfRuleFundDto [fundId=" + fundId + ", isinCode=" + isinCode + ", fundName=" + fundName + "]";
	}

}
