package com.trading.mfanalyser.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

@Document("reportdata")
public class MfStockReportDocument {
	
	@Id
	private String _id;

	private String ruleType;
	private String stockName;
	private String isinCode;
	private String sector;
	private String holdingTrend;
	private double day1;
	private double day2;
	private double day3;
	private double day4;
	private double day5;
	private double week1;
	private double week2;
	private double week3;
	private int ruleRank;
	private double ruleAvg;
	
	private LocalDate lastRunDate;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;


	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
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

	public String getHoldingTrend() {
		return holdingTrend;
	}

	public void setHoldingTrend(String holdingTrend) {
		this.holdingTrend = holdingTrend;
	}

	
	public int getRuleRank() {
		return ruleRank;
	}

	public void setRuleRank(int ruleRank) {
		this.ruleRank = ruleRank;
	}

	public double getRuleAvg() {
		return ruleAvg;
	}

	public void setRuleAvg(double ruleAvg) {
		this.ruleAvg = ruleAvg;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MfStockReportDocument other = (MfStockReportDocument) obj;
		return Objects.equals(_id, other._id);
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
