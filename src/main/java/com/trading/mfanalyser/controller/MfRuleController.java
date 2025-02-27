package com.trading.mfanalyser.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trading.mfanalyser.dto.MfRuleDto;
import com.trading.mfanalyser.dto.MfStockReportDto;
import com.trading.mfanalyser.dto.StockFundNameInfo;
import com.trading.mfanalyser.service.MfRuleService;

@RestController
@RequestMapping
public class MfRuleController {
	
	Logger logger = LoggerFactory.getLogger(MfRuleController.class);
	@Autowired
	MfRuleService mfRuleService;
	
	@RequestMapping(value = "/listMfRuleRecords", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MfRuleDto> listMfRuleRecords() throws Exception {
		return mfRuleService.listMfRuleRecords();
	}
	
	@RequestMapping(value = "/listMfStockTrendRecords/{ruleType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MfStockReportDto> listMfStockTrendRecords(@PathVariable("ruleType") String ruleType) throws Exception {
		return mfRuleService.listMfStockTrendRecords(ruleType);
	}
	
	@RequestMapping(value = "/listMfStockFunds", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StockFundNameInfo> listMfStockFunds(@RequestParam("ruleId") long ruleId, @RequestParam("stockName") String stockName) throws Exception {
		return mfRuleService.listMfStockFunds(ruleId, stockName);
	}
}
