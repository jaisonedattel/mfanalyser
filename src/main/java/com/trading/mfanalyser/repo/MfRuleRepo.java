package com.trading.mfanalyser.repo;

import org.springframework.data.repository.CrudRepository;

import com.trading.mfanalyser.entity.MfRule;

public interface MfRuleRepo  extends CrudRepository<MfRule,Long> {

}
