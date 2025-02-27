package com.trading.mfanalyser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trading.mfanalyser.service.MfRuleDataProcessService;

@RestController
@RequestMapping
public class MfDataProcessController {
	
	@Autowired
	MfRuleDataProcessService ruleUpdaetService;
		
	@RequestMapping(value = "/refreshRuleData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String refreshRuleData() throws Exception {
		return ruleUpdaetService.refreshRuleData();
	}

	
	@RequestMapping(value = "/refreshStockReport/{ruleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String refreshStockReport(@PathVariable("ruleId") long ruleId) throws Exception {
		ruleUpdaetService.invokeStokReportRefresh(ruleId);
		return "hello";
	}
	@RequestMapping(value = "testjpadelete", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String hello(ModelMap model) {
		//ruleUpdaetService.testJpaDelete();
		return "hello";
	}
	
}
