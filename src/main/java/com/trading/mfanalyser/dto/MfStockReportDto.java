package com.trading.mfanalyser.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.trading.mfanalyser.util.ArrayNodeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

public class MfStockReportDto {
	
	private long reportId;

	private long ruleId;

	private String stockName;

	private String isinCode;

	private String sector;

	/*
	 * data structure : [[DATE,FUND_COUNT, AVG_PER,MIN,MAX,0,0,0],[],[]]
	 */
	private ArrayNode holdingTrend;

	private double day1;

	private double day2;

	private double day3;

	private double day4;

	private double day5;

	private double week1;

	private double week2;

	private double week3;

	private LocalDate lastRunDate;
	
	private LocalDateTime createdDate;

	private LocalDateTime updatedDate;


	public long getReportId() {
		return reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
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

	public ArrayNode getHoldingTrend() {
		return holdingTrend;
	}

	public void setHoldingTrend(ArrayNode holdingTrend) {
		this.holdingTrend = holdingTrend;
	}

	public double getDay1() {
		return day1;
	}

	public void setDay1(double day1) {
		this.day1 = day1;
	}

	public double getDay2() {
		return day2;
	}

	public void setDay2(double day2) {
		this.day2 = day2;
	}

	public double getDay3() {
		return day3;
	}

	public void setDay3(double day3) {
		this.day3 = day3;
	}

	public double getDay4() {
		return day4;
	}

	public void setDay4(double day4) {
		this.day4 = day4;
	}

	public double getDay5() {
		return day5;
	}

	public void setDay5(double day5) {
		this.day5 = day5;
	}

	public double getWeek1() {
		return week1;
	}

	public void setWeek1(double week1) {
		this.week1 = week1;
	}

	public double getWeek2() {
		return week2;
	}

	public void setWeek2(double week2) {
		this.week2 = week2;
	}

	public double getWeek3() {
		return week3;
	}

	public void setWeek3(double week3) {
		this.week3 = week3;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public LocalDate getLastRunDate() {
		return lastRunDate;
	}

	public void setLastRunDate(LocalDate lastRunDate) {
		this.lastRunDate = lastRunDate;
	}

}
