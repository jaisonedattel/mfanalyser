package com.trading.mfanalyser.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.trading.mfanalyser.entity.MfRule;
import com.trading.mfanalyser.entity.MfRuleFund;
import com.trading.mfanalyser.entity.MfRuleFundHistory;
import com.trading.mfanalyser.entity.MfRuleFundHolding;
import com.trading.mfanalyser.entity.MfRuleFundHoldingHistory;
import com.trading.mfanalyser.entity.MfStockReportDocument;
import com.trading.mfanalyser.entity.MfStockReportEntity;
import com.trading.mfanalyser.repo.MfRuleFundHistoryRepo;
import com.trading.mfanalyser.repo.MfRuleFundHoldingHistoryRepo;
import com.trading.mfanalyser.repo.MfRuleFundHoldingRepo;
import com.trading.mfanalyser.repo.MfRuleFundRepo;
import com.trading.mfanalyser.repo.MfRuleRepo;
import com.trading.mfanalyser.repo.MfRuleStockReportRepo;
import com.trading.mfanalyser.repo.StockReportMongoRepo;

import jakarta.transaction.Transactional;

@Service
public class MfRuleDataProcessService {

	Logger logger = LoggerFactory.getLogger(MfRuleDataProcessService.class);

	public static final String listTopFunds = "https://marketapi.intoday.in/widget/bestmutualfunds/view?duration=5y&broad_category_group=Equity&fund_level_category_name=Large-Cap&page=1";
	public static final String listFundsHolding = "https://marketapi.intoday.in/widget/mfstockholding/pullview?isin=";

	public static final int MAX_FUND_COUNT = 10;
	public static final int MAX_HOLDINGS_COUNT = 50;
	public static final int MAX_TREND_DATA_COUNT = 50;

	public static final int ARRAY_RANK_IDX = 4;

	@Autowired
	MfRuleRepo mfRuleRepo;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	MfRuleFundRepo mfRuleFundRepo;

	@Autowired
	MfRuleFundHoldingRepo mfRuleFundHoldingRepo;

	@Autowired
	MfRuleFundHistoryRepo mfRuleFundHistoryRepo;

	@Autowired
	MfRuleFundHoldingHistoryRepo mfRuleFundHoldingHistoryRepo;

	@Autowired
	MfRuleStockReportRepo mfRuleStockReportRepo;

	@Autowired
	StockReportMongoRepo stockReportMongoRepo;

	public void createMfRule() {
		MfRule mfRule = new MfRule();
		mfRule.setDescription("Top Large Cap fund in 5yr -- III");
		mfRule.setRuleName("Equity Large Cap");
		mfRuleRepo.save(mfRule);

	}

	@Transactional
	public void loadMfRuleData(MfRule mfRule) throws Exception {
		logger.info("loadMfRuleData :" + mfRule.getRuleType());
		LocalDate currDate = LocalDate.now();
		int monthId = (currDate.getYear() * 100) + currDate.getMonthValue();
		boolean isFundDataRefreshed = false;
		long fundCountForCurrMonth = mfRuleFundRepo.getCountOfFundForMonth(mfRule.getRuleId(), monthId);
		/*
		 * Fund data has to be re-loaded every Month
		 */
		if (fundCountForCurrMonth == 0) {
			logger.info("loadMfRuleData : Refreshing MF list data" + mfRule.getRuleType());

			isFundDataRefreshed = true;
			mfRuleFundHoldingRepo.deleteByRuleId(mfRule.getRuleId());
			mfRuleFundRepo.deleteByRuleId(mfRule.getRuleId());

			JsonNode fundNode = getRestApiResponse(mfRule.getDataApiUrl());
			JsonNode fundArray = fundNode.get("data");

			List<MfRuleFund> fundList = new ArrayList<MfRuleFund>();

			for (JsonNode fundJson : fundArray) {
				MfRuleFund fund = new MfRuleFund();
				fund.setMfRule(mfRule);
				fund.setMonthId(monthId);
				fund.setFundCategory(fundJson.get("broad_category_group").asText());
				fund.setFundSubCategory(fundJson.get("fund_level_category_name").asText());
				fund.setFundName(fundJson.get("legal_name").asText());
				fund.setIsinCode(fundJson.get("isin").asText());
				fund.setFundReturn1Yr(fundJson.get("dp_return_1Yr").asDouble());
				fund.setFundReturn3Yr(fundJson.get("dp_return_3Yr").asDouble());
				fund.setFundReturn5Yr(fundJson.get("dp_return_5Yr").asDouble());
				fundList.add(fund);
				if (fundList.size() >= MAX_FUND_COUNT) {
					break;
				}
			}
			mfRule.setMfRuleFund(fundList);
		}
		/*
		 * Holding data has to be re-loaded every day
		 */
		mfRuleFundHoldingRepo.deleteByRuleId(mfRule.getRuleId());
		logger.info("loadMfRuleData : Refreshing MF Holding list data :" + mfRule.getRuleType());
		for (MfRuleFund fund : mfRule.getMfRuleFund()) {
			fund.setMfRuleFundHolding(getMfFundHoldings(fund, fund.getIsinCode()));
		}

		MfRule mfRuleS = mfRuleRepo.save(mfRule);
		//saveDataToHistoryTable(mfRuleS, isFundDataRefreshed);
		refreshStockReportData(mfRuleS);
		logger.info("loadMfRuleData : Rule Data refresh completed : " + mfRule.getRuleType());
	}

	private void saveDataToHistoryTable(MfRule mfRule, boolean isFundDataRefreshed) {
		logger.info("saveDataToHistoryTable ");
		List<MfRuleFundHistory> fundHistoryList = new ArrayList<MfRuleFundHistory>();
		List<MfRuleFundHoldingHistory> fundHoldingHistoryList = new ArrayList<MfRuleFundHoldingHistory>();

		mfRule.getMfRuleFund().forEach(fund -> {
			// Save Fund to history only if its refreshed...
			if (!isFundDataRefreshed) {
				MfRuleFundHistory fundH = new MfRuleFundHistory();
				BeanUtils.copyProperties(fund, fundH);
				fundH.setRuleId(mfRule.getRuleId());
				fundHistoryList.add(fundH);
			}
			fund.getMfRuleFundHolding().forEach(holding -> {
				MfRuleFundHoldingHistory holdingH = new MfRuleFundHoldingHistory();
				BeanUtils.copyProperties(holding, holdingH);
				holdingH.setFundId(fund.getFundId());
				fundHoldingHistoryList.add(holdingH);
			});
		});

		mfRuleFundHistoryRepo.saveAll(fundHistoryList);
		mfRuleFundHoldingHistoryRepo.saveAll(fundHoldingHistoryList);
	}

	private List<MfRuleFundHolding> getMfFundHoldings(MfRuleFund fund, String isinCode) throws Exception {
		List<MfRuleFundHolding> fundHoldingList = new ArrayList<MfRuleFundHolding>();

		JsonNode fundNode = getRestApiResponse(listFundsHolding + isinCode);
		JsonNode fundArray = fundNode.get("data");
		for (JsonNode fundJson : fundArray) {
			MfRuleFundHolding holding = new MfRuleFundHolding();
			holding.setMfRuleFund(fund);
			holding.setRuleId(fund.getMfRule().getRuleId());
			holding.setStockName(fundJson.get("stock_name").asText());
			holding.setGlobalIndustry(fundJson.get("global_industry").asText());
			holding.setIsinCode(fundJson.get("stock_isin").asText());
			holding.setSector(fundJson.get("sector_name").asText());
			holding.setHoldingPercentage(fundJson.get("weighting").asDouble());
			fundHoldingList.add(holding);
//			if (fundHoldingList.size() >= 10)
//				break;
		}
		return fundHoldingList;
	}

	@Transactional
	public void invokeStokReportRefresh(Long ruleId) {
		logger.info("invokeStokReportRefresh :" + ruleId);
		MfRule mfRule = mfRuleRepo.findById(ruleId).get();
		refreshStockReportData(mfRule);
	}

	private void refreshStockReportData(MfRule mfRule) {
		LocalDate currDate = LocalDate.now(ZoneId.of("GMT+05:30"));
		String currDateStr = currDate.toString();
		logger.info("Refresh Stock Report Data : " + mfRule.getRuleName());
		long recCount = mfRuleStockReportRepo.getCountOfReportForDate(mfRule.getRuleType(), currDate);
		if (recCount == 0) {
			List<MfStockReportEntity> reportList = mfRuleStockReportRepo
					.findByRuleTypeOrderByDay1Desc(mfRule.getRuleType());
			Map<String, MfStockReportEntity> reportMap = reportList.stream()
					.collect(Collectors.toMap(MfStockReportEntity::getStockName, Function.identity()));

			Map<String, List<MfRuleFundHolding>> holdingMap = mfRule.getMfRuleFund().stream()
					.flatMap(fund -> fund.getMfRuleFundHolding().stream()).collect(Collectors.groupingBy(
							MfRuleFundHolding::getStockName, HashMap::new, Collectors.toCollection(ArrayList::new)));

			holdingMap.forEach((stockNameKey, fundHoldingList) -> {
				int holdingCount = fundHoldingList.size();
				if (holdingCount > 1) {

					MfStockReportEntity reportEntity = reportMap.get(stockNameKey);
					if (reportEntity == null) {
						reportEntity = new MfStockReportEntity(fundHoldingList.get(0));
						reportEntity.setRuleType(mfRule.getRuleType());
						//
						reportEntity.setHoldingTrend(mapper.createArrayNode());
						reportMap.put(stockNameKey, reportEntity);
					}
					updateTrendData(currDateStr, fundHoldingList, reportEntity);
					/**
					 * 
					 */
					reportEntity.setLastRunDate(currDate); // crucial
				}

			});
			List<MfStockReportEntity> stockList = new ArrayList<MfStockReportEntity>(reportMap.values());
			setStockHoldingRank(stockList);
			mfRuleStockReportRepo.saveAll(stockList);
			backUpDataToMongoDb(mfRule.getRuleType(), stockList);
		}
	}

	private void setStockHoldingRank(List<MfStockReportEntity> stockList) {

		Comparator<MfStockReportEntity> myComparator = new StockRankingComparator();
		Collections.sort(stockList, myComparator);
		int rank = 0;
		for (MfStockReportEntity stock : stockList) {
			rank++;
			stock.setRuleRank(rank);
			ArrayNode trendData = stock.getHoldingTrend();
			ArrayNode latestNode = (ArrayNode) trendData.get(trendData.size() - 1);
			latestNode.set(ARRAY_RANK_IDX, rank);
		}
	}

	private void backUpDataToMongoDb(String ruleType, List<MfStockReportEntity> stockList) {

		List<MfStockReportDocument> rptDocList = stockReportMongoRepo.findAll();
		Map<String, MfStockReportDocument> rptDocMap = rptDocList.stream()
				.collect(Collectors.toMap(MfStockReportDocument::get_id, Function.identity()));

		stockList.forEach(rptEntity -> {
			String docId = rptEntity.getRuleType() + "_" + (rptEntity.getStockName()).replaceAll(" ", "_");
			MfStockReportDocument doc = rptDocMap.get(docId);
			if (doc == null) {
				doc = new MfStockReportDocument();
				doc.set_id(docId);
				rptDocList.add(doc);
			}
			BeanUtils.copyProperties(rptEntity, doc);
			doc.setHoldingTrend(rptEntity.getHoldingTrend().toString());
		});

		stockReportMongoRepo.saveAll(rptDocList);
	}

	private void updateTrendData(String currDate, List<MfRuleFundHolding> fundHoldingList,
			MfStockReportEntity reportEntity) {

		ArrayNode trendData = reportEntity.getHoldingTrend();
		if (trendData == null) {
			return;
		}
		if (trendData.size() >= 1) { //For Newly added stock, trend data will be empty
			ArrayNode latestNode = (ArrayNode) trendData.get(trendData.size() - 1);
			/* If date exists return */
			if (latestNode.get(0).asText().equals(currDate)) {
				return;
			}
		}
		/* Remove if trend data count exceed max count */
		while (trendData.size() > MAX_TREND_DATA_COUNT) {
			trendData.remove(0);
		}
		
		Supplier<DoubleStream> weightageStrm = () -> fundHoldingList.stream()
				.mapToDouble(MfRuleFundHolding::getHoldingPercentage);
		double avg = Math.floor(weightageStrm.get().average().orElse(Double.NaN) * 100) / 100;
		double sum = Math.floor(weightageStrm.get().sum() * 100) / 100;
		double max = Math.floor(weightageStrm.get().max().orElse(Double.NaN) * 100) / 100;
		reportEntity.setDay5(reportEntity.getDay4());
		reportEntity.setDay4(reportEntity.getDay3());
		reportEntity.setDay3(reportEntity.getDay2());
		reportEntity.setDay2(reportEntity.getDay1());
		reportEntity.setDay1(avg);
		double ruleWiseAvg = sum / MAX_FUND_COUNT;
		reportEntity.setRuleAvg(ruleWiseAvg);

		Set<String> fundSet = new HashSet<>();
		fundHoldingList.stream().filter(p -> fundSet.add(p.getMfRuleFund().getFundName())).collect(Collectors.toList());

		ArrayNode childArr = trendData.addArray();
		childArr.add(currDate);
		childArr.add(fundSet.size());
		childArr.add(avg);
		childArr.add(ruleWiseAvg);
		childArr.add(0); // placeholder for Stock Rank..................ARRAY_RANK_IDX - 4
		// empty record for future use
		childArr.add(0);
		childArr.add(0);
		childArr.add(0);
	}

	public JsonNode getRestApiResponse(String url) throws Exception {
		WebClient client = WebClient.builder().baseUrl(url).defaultCookie("cookieKey", "cookieValue")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
		String jsonStr = client.get().retrieve().bodyToMono(String.class).block();
		JsonNode fundNode = mapper.readTree(jsonStr);

		return fundNode;

	}

	@Transactional
	@Scheduled(cron = "0 0 2,13 * * MON-SAT", zone = "Asia/Kolkata")
	public String refreshRuleData() {
		logger.info("refreshRuleData : " + LocalDateTime.now(ZoneId.of("GMT+05:30")));
		List<MfRule> mfRuleList = mfRuleRepo.findByIsActive(MfRuleService.ACTIVE);
		mfRuleList.forEach(mfRule -> {
			try {
				loadMfRuleData(mfRule);
			} catch (Exception e) {
				logger.error("Error in data refresh : ", e);
			}
		});

		return "Success";
	}

	public static void main(String arg[]) {
		ObjectMapper objM = new ObjectMapper();
		ArrayNode parentNode = objM.createArrayNode();
		parentNode.addArray().add(1);
		parentNode.addArray().add(2);
		;
		parentNode.addArray().add(3);
		;
		parentNode.addArray().add(4);
		;
		parentNode.addArray().add(5);
		;
		parentNode.addArray().add(6);
		;
		parentNode.addArray().add(7);
		;
		parentNode.addArray().add(8);
		;
		parentNode.addArray().add(9);
		;
		parentNode.addArray().add(10);
		;
		System.out.println("Parent Node size :" + parentNode);

		while (parentNode != null && parentNode.size() > 5) {
			parentNode.remove(0);
		}
		parentNode.addArray().add(11);
		System.out.println("Parent Node size :" + parentNode);

		System.out.println("India time now " + LocalDateTime.now(ZoneId.of("GMT+05:30")));
		System.out.println("India time now " + LocalDateTime.now(ZoneId.of("GMT+05:00")));
	}
}
