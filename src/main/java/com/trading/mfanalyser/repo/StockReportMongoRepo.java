package com.trading.mfanalyser.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.trading.mfanalyser.entity.MfStockReportDocument;

public interface StockReportMongoRepo extends MongoRepository<MfStockReportDocument, String> {
    
    //@Query("{name:'?0'}")
    
	List<MfStockReportDocument> findByRuleType(String ruleType);
    
    //public long count();

}