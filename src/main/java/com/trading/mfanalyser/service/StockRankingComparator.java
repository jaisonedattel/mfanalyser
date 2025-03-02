package com.trading.mfanalyser.service;

import java.util.Comparator;

import com.trading.mfanalyser.entity.MfStockReportEntity;

public class StockRankingComparator implements Comparator<MfStockReportEntity> 
{

	@Override
	public int compare(MfStockReportEntity o1, MfStockReportEntity o2) {
		if(o1.getRuleAvg() < o2.getRuleAvg()) {
			return 1;
		}else if(o1.getRuleAvg() > o2.getRuleAvg()) {
			return -1;
		}
		return o1.getStockName().compareTo(o2.getStockName());
	}

}
