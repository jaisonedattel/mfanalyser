package com.trading.mfanalyser.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.mfanalyser.entity.MfRule;
import com.trading.mfanalyser.repo.MfRuleFundHistoryRepo;
import com.trading.mfanalyser.repo.MfRuleFundHoldingHistoryRepo;
import com.trading.mfanalyser.repo.MfRuleFundHoldingRepo;
import com.trading.mfanalyser.repo.MfRuleFundRepo;
import com.trading.mfanalyser.repo.MfRuleRepo;

import jakarta.transaction.Transactional;

@Service
public class DbUpdateTestService {

	Logger logger = LoggerFactory.getLogger(DbUpdateTestService.class);

	ObjectMapper jsonMapper = new ObjectMapper();

	@Autowired
	MfRuleRepo mfRuleRepo;

	@Autowired
	MfRuleFundRepo mfRuleFundRepo;

	@Autowired
	MfRuleFundHoldingRepo mfRuleFundHoldingRepo;

	@Autowired
	MfRuleFundHistoryRepo mfRuleFundHistoryRepo;

	@Autowired
	MfRuleFundHoldingHistoryRepo mfRuleFundHoldingHistoryRepo;

	@Transactional
	public void testJpaDelete() {

		// 14952
		/*
		 * 9952 9953 9954 9955 9956
		 * 
		 * 9952 9953 9954 9955 9956 9957 9958 9959 9960 9961 9962
		 * 
		 */
		/*
		 * MfRuleFundHolding stock = mfRuleFundHoldingRepo.findById(9952L).get();
		 * System.out.println("Stock :"+stock); mfRuleFundHoldingRepo.delete(stock);
		 * System.out.println("deleted Stock exists :"+mfRuleFundHoldingRepo.existsById(
		 * 9952L));
		 * 
		 * MfRuleFund fund = mfRuleFundRepo.findById(9956L).get();
		 * System.out.println("Fund :"+fund);
		 * System.out.println("Fund Stock :"+fund.getMfRuleFundHolding().get(0)); long
		 * fstock = fund.getMfRuleFundHolding().get(0).getHoldingId();
		 * mfRuleFundRepo.delete(fund);
		 * System.out.println("deleted Fund exists :"+mfRuleFundRepo.existsById(9956L));
		 * System.out.println("deleted FundStock exists :"+mfRuleFundHoldingRepo.
		 * existsById(fstock));
		 * 
		 * 
		 * MfRule rule = mfRuleRepo.findById(14952l).get(); mfRuleRepo.delete(rule);
		 * System.out.println("deleted Rule exists :"+mfRuleRepo.existsById(14952l));
		 * 
		 * MfRule rule1 = mfRuleRepo.findById(14952l).get(); List<MfRuleFund> ruleFunds
		 * = rule1.getMfRuleFund();//mfRuleFundRepo.getAllFundByRule(14952l);
		 * ruleFunds.forEach(fund -> { System.out.println("Deleting fund "+fund);
		 * 
		 * mfRuleFundRepo.delete(fund);}); //Below statement is mandatory to completly
		 * delete the child record... rule1.getMfRuleFund().removeAll(ruleFunds);
		 * System.out.println("deleted Fund exists :"+rule1.getMfRuleFund());
		 */

		/*
		 * Below is Most safe way to delete child records FetchType.LAZY may create
		 * issue while deleting child records from Parent Object....
		 */
		MfRule rule1 = mfRuleRepo.findById(14952l).get();
		System.out.println(rule1.getMfRuleFund());
		mfRuleFundHoldingRepo.deleteByRuleId(14952);
		mfRuleFundRepo.deleteByRuleId(14952);
		System.out.println("deleted Fund exists :" + mfRuleFundRepo.existsById(14952l));
		System.out.println(rule1.getMfRuleFund());

	}
}
