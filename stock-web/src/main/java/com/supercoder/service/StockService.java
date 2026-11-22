package com.supercoder.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import com.supercoder.entity.USStockRss;

@Service
public interface StockService {

    public void saveStockNews(USStockRss stockRss);

    public Boolean isStockNewsExist(String stockCode, String link);

    public Long getStockUnusualCount(USStockRss stockRss, LocalDateTime startTime, LocalDateTime endTime);
}
