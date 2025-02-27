package com.trading.mfanalyser.config;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.trading.mfanalyser.entity.MfRule;
import com.trading.mfanalyser.repo.MfRuleRepo;

@Component
public class RuleDataImportConfig implements ApplicationListener<ApplicationReadyEvent>  {

	@Autowired
	MfRuleRepo mfRuleRepo;
	/**
	   * This event is executed as late as conceivably possible to indicate that 
	   * the application is ready to service requests.
	   */
	  @Override
	  public void onApplicationEvent(final ApplicationReadyEvent event) {
		  mfRuleRepo.save(getRuleEntity(1001, "EQUITY_LARGE_CAP", "Equity Large Cap", "Top Large Cap fund in 3yr", "Y", "https://marketapi.intoday.in/widget/bestmutualfunds/view?duration=3y&broad_category_group=Equity&fund_level_category_name=Large-Cap&page=1"));
		  mfRuleRepo.save(getRuleEntity(1002, "EQUITY_MID_CAP", "Equity Mid Cap", "Top Mid Cap fund in 3yr", "Y", "https://marketapi.intoday.in/widget/bestmutualfunds/view?duration=3y&broad_category_group=Equity&fund_level_category_name=Mid-Cap&page=1"));
		  mfRuleRepo.save(getRuleEntity(1003, "EQUITY_SMALL_CAP", "Equity Small Cap", "Top Small Cap fund in 3yr", "Y", "https://marketapi.intoday.in/widget/bestmutualfunds/view?duration=3y&broad_category_group=Equity&fund_level_category_name=Small-Cap&page=1"));
	  }
	  
	  private MfRule getRuleEntity(long ruleId, String ruleType, String ruleName, String desc, String isActive, String apiUrl) {
			MfRule mfRule = new MfRule();
			mfRule.setRuleId(ruleId);;
			mfRule.setRuleType(ruleType);
			mfRule.setDescription(desc);
			mfRule.setRuleName(ruleName);
			mfRule.setIsActive(isActive);
			mfRule.setDataApiUrl(apiUrl);
			mfRule.setCreatedDate(LocalDate.now());
			return mfRule;
	  }
}
