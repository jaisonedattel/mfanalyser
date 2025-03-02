package com.trading.mfanalyser.config;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.trading.mfanalyser.entity.MfRule;
import com.trading.mfanalyser.entity.MfStockReportDocument;
import com.trading.mfanalyser.entity.MfStockReportEntity;
import com.trading.mfanalyser.repo.MfRuleRepo;
import com.trading.mfanalyser.repo.MfRuleStockReportRepo;
import com.trading.mfanalyser.repo.StockReportMongoRepo;
import com.trading.mfanalyser.service.MfRuleService;

@Component
public class RuleDataImportConfig implements ApplicationListener<ApplicationReadyEvent> {

	Logger logger = LoggerFactory.getLogger(RuleDataImportConfig.class);

	@Autowired
	MfRuleRepo mfRuleRepo;

	@Autowired
	MfRuleStockReportRepo mfRuleStockReportRepo;
	
	
	@Autowired
	StockReportMongoRepo stockReportMongoRepo;

	@Autowired
	ObjectMapper mapper;
	
	/**
	 * This event is executed as late as conceivably possible to indicate that the
	 * application is ready to service requests.
	 */
	@Async
	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		logger.info("On Startup Loading Rule data, if not exists");
		try {
			if (mfRuleRepo.findByRuleType(MfRuleService.EQUITY_LARGE_CAP) == null) {
				mfRuleRepo.save(getRuleEntity(MfRuleService.EQUITY_LARGE_CAP, "Equity Large Cap",
						"Top Large Cap fund in 3yr", "Y",
						"https://marketapi.intoday.in/widget/bestmutualfunds/view?duration=3y&broad_category_group=Equity&fund_level_category_name=Large-Cap&page=1"));
			}
			if (mfRuleRepo.findByRuleType(MfRuleService.EQUITY_MID_CAP) == null) {
				mfRuleRepo.save(getRuleEntity(MfRuleService.EQUITY_MID_CAP, "Equity Mid Cap", "Top Mid Cap fund in 3yr",
						"Y",
						"https://marketapi.intoday.in/widget/bestmutualfunds/view?duration=3y&broad_category_group=Equity&fund_level_category_name=Mid-Cap&page=1"));
			}
			if (mfRuleRepo.findByRuleType(MfRuleService.EQUITY_SMALL_CAP) == null) {
				mfRuleRepo.save(getRuleEntity(MfRuleService.EQUITY_SMALL_CAP, "Equity Small Cap",
						"Top Small Cap fund in 3yr", "Y",
						"https://marketapi.intoday.in/widget/bestmutualfunds/view?duration=3y&broad_category_group=Equity&fund_level_category_name=Small-Cap&page=1"));
			}
			
			loadReportDataIfEmpty();
		} catch (Exception e) {
			logger.error("Error in loading Application setup data",e);
		}
		logger.info("On Startup Loading Rule data, if not exists - Completed");
	}

	private MfRule getRuleEntity(String ruleType, String ruleName, String desc, String isActive, String apiUrl) {
		MfRule mfRule = new MfRule();
		// mfRule.setRuleId(ruleId);;
		mfRule.setRuleType(ruleType);
		mfRule.setDescription(desc);
		mfRule.setRuleName(ruleName);
		mfRule.setIsActive(isActive);
		mfRule.setDataApiUrl(apiUrl);
		mfRule.setCreatedDate(LocalDate.now());
		return mfRule;
	}
	
	private void loadReportDataIfEmpty() {
		logger.info("On Startup Loading Report data, if not exists");
		long rptCount = mfRuleStockReportRepo.count();
		if(rptCount > 0) {
			logger.info("On Startup Loading Report data, Skipped");
			return;
		}
		List<MfStockReportEntity> reportList = new ArrayList<MfStockReportEntity>();
		List<MfStockReportDocument> rptDocList = stockReportMongoRepo.findAll();
		rptDocList.forEach(doc -> {
			MfStockReportEntity reportEntity =  new MfStockReportEntity();
			BeanUtils.copyProperties(doc, reportEntity);
			reportEntity.setHoldingTrend(convertToEntityAttribute(doc.getHoldingTrend()));
			reportList.add(reportEntity);
		});
		logger.info("On Startup Loading Report data, if not exists - Completed....");
		mfRuleStockReportRepo.saveAll(reportList);
	}
	public ArrayNode convertToEntityAttribute(String dbData) {
		try {
			if (!StringUtils.hasText(dbData)) {
				return mapper.createArrayNode();
			}
			return (ArrayNode) mapper.readTree(dbData);
		} catch (Exception e) {
			logger.error("Exception while converting to entity attribute", e);
			return null;
		}
	}
}
