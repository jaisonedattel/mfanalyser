package com.trading.mfanalyser.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.mfanalyser.dto.MfRuleDto;
import com.trading.mfanalyser.dto.MfRuleFundDto;
import com.trading.mfanalyser.dto.MfRuleInfo;
import com.trading.mfanalyser.dto.MfStockReportDto;
import com.trading.mfanalyser.dto.StockFundNameInfo;
import com.trading.mfanalyser.entity.MfRule;
import com.trading.mfanalyser.entity.MfRuleFund;
import com.trading.mfanalyser.entity.MfStockReportEntity;
import com.trading.mfanalyser.repo.MfRuleFundHoldingRepo;
import com.trading.mfanalyser.repo.MfRuleFundRepo;
import com.trading.mfanalyser.repo.MfRuleRepo;
import com.trading.mfanalyser.repo.MfRuleStockReportRepo;

@Service
public class MfRuleService {

	Logger logger = LoggerFactory.getLogger(MfRuleService.class);
	
	public static final String ACTIVE = "Y";
	
	@Autowired
	MfRuleRepo mfRuleRepo;

	@Autowired
	MfRuleFundRepo mfRuleFundRepo;

	@Autowired
	MfRuleFundHoldingRepo mfRuleFundHoldingRepo;
	
	@Autowired
	MfRuleStockReportRepo reportRepo;
	
	public List<MfRuleDto> listMfRuleRecords() {
		logger.info("listMfRuleRecords");
		List<MfRule> mfRuleList = mfRuleRepo.findByIsActive(ACTIVE);
		List<MfRuleDto> mfRuleListRet = new ArrayList<MfRuleDto>();
		for(MfRule rule : mfRuleList) {
			MfRuleDto ret = new MfRuleDto();
			BeanUtils.copyProperties(rule, ret);
			for(MfRuleFund fund : rule.getMfRuleFund()) {
				MfRuleFundDto funddto = new MfRuleFundDto();
				BeanUtils.copyProperties(fund, funddto);
				ret.getMfRuleFund().add(funddto);
			}
			mfRuleListRet.add(ret);
		}
		
		return mfRuleListRet;
	}
	public List<MfRuleInfo> listProjectionMfRuleRecords() {
		logger.info("listMfRuleRecords");
		List<MfRuleInfo> ruleList =  mfRuleRepo.getAllActiveRuleWithFund(ACTIVE);
		List<MfRuleInfo> retList = new ArrayList<MfRuleInfo>();
		long prevRuleId = 0;
		MfRuleInfo currDto = null;
		for(MfRuleInfo dto : ruleList ) {
			long ruleId = dto.getRuleId();
			if(prevRuleId != ruleId) {
				retList.add(dto);
				currDto = dto;
				prevRuleId = ruleId;
			}else {
				currDto.getMfRuleFund().add(dto.getMfRuleFund().get(0));
			}
		}
		System.out.println(retList);
		return retList;

	}
	public List<MfStockReportDto> listMfStockTrendRecords(long ruleId) {
		List<MfStockReportEntity> reportEntityList = reportRepo.findByRuleIdOrderByDay1Desc(ruleId);
		List<MfStockReportDto> reportDtoList = new ArrayList<MfStockReportDto>();
		reportEntityList.forEach(entity -> {
			MfStockReportDto dto = new MfStockReportDto();
			BeanUtils.copyProperties(entity, dto);
			reportDtoList.add(dto);
		});
		return reportDtoList;
	}
	public List<StockFundNameInfo> listMfStockFunds(long ruleId, String stockName) {
		return mfRuleFundHoldingRepo.getStockHoldingFundNames(ruleId, stockName);
	}

}
