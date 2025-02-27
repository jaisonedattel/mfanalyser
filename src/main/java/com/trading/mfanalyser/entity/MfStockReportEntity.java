package com.trading.mfanalyser.entity;

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

@Entity
@Table(name = "T_MF_RULE_STOCK_REPORT", schema = "APP")
public class MfStockReportEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REPORT_ID")
	private long reportId;

	@Column(name = "RULE_TYPE")
	private String ruleType;

	@Column(name = "STOCK_NAME")
	private String stockName;

	@Column(name = "ISIN_CODE")
	private String isinCode;

	@Column(name = "SECTOR")
	private String sector;

	/*
	 * data structure : [[DATE,FUND_COUNT, AVG_PER,MIN,MAX,0,0,0],[],[]]
	 */
	@Convert(converter = ArrayNodeConverter.class)
	@Column(name = "HOLDING_TREND")
	@Lob
	private ArrayNode holdingTrend;

	@Column(name = "DAY1")
	private double day1;

	@Column(name = "DAY2")
	private double day2;

	@Column(name = "DAY3")
	private double day3;

	@Column(name = "DAY4")
	private double day4;

	@Column(name = "DAY5")
	private double day5;

	@Column(name = "WEEK1")
	private double week1;

	@Column(name = "WEEK2")
	private double week2;

	@Column(name = "WEEK3")
	private double week3;

	@Column(name = "LAST_RUN_DATE", columnDefinition = "DATE")
	private LocalDate lastRunDate;
	
	@Column(name = "CREATED_DATE", columnDefinition = "TIMESTAMP")
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "UPDATED_DATE", columnDefinition = "TIMESTAMP")
	@UpdateTimestamp
	private LocalDateTime updatedDate;

	public MfStockReportEntity() {
	}

	public MfStockReportEntity(MfRuleFundHolding stock) {
		BeanUtils.copyProperties(stock, this);
	}

	public long getReportId() {
		return reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
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

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public static void main(String arg[]) throws JsonMappingException, JsonProcessingException {
		String lobData = "[[\"19-20-2925\",9,12,15,9]]";
		ObjectMapper objectMapper = new ObjectMapper();
		// ArrayNode parentArray = objectMapper.createArrayNode();
		ArrayNode parentArray = (ArrayNode) objectMapper.readTree(lobData);
		ArrayNode childArr = objectMapper.createArrayNode();
		childArr.add(LocalDate.now().toString());
		childArr.add(9);
		childArr.add(12);
		childArr.add(15);
		childArr.add(9);
		childArr.add(0);
		parentArray.add(childArr);
		System.out.println(" ArrayNode array " + parentArray.toString());

		System.out.println(" ArrayNode array " + objectMapper.writeValueAsString(parentArray));
		System.out.println(objectMapper.createArrayNode().toString());

	}

}
