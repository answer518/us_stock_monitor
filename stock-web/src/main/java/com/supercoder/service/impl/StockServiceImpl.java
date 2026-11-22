package com.supercoder.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.supercoder.entity.USStockRss;
import com.supercoder.mapper.USStockRssMapper;
import com.supercoder.service.StockService;

import jakarta.annotation.Resource;

@Service 
public class StockServiceImpl implements StockService {

    @Resource
    private USStockRssMapper usStockRssMapper;

    @Override
    public void saveStockNews(USStockRss stockRss) {
        usStockRssMapper.insert(stockRss);
    }

    @Override
    public Boolean isStockNewsExist(String stockCode, String link) {
        QueryWrapper<USStockRss> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stock_code", stockCode);
        queryWrapper.eq("link", link);
        return usStockRssMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public Long getStockUnusualCount(USStockRss stockRss, LocalDateTime startTime, LocalDateTime endTime) {
        String stockCode = stockRss.getStockCode();
        QueryWrapper<USStockRss> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stock_code", stockCode);

        DateTimeFormatter  formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startDateStr = formatter.format(startTime);
        String endDateStr = formatter.format(endTime);
        queryWrapper.le("pub_date_gmt", endDateStr);
        queryWrapper.ge("pub_date_gmt", startDateStr);
        
        return usStockRssMapper.selectCount(queryWrapper);
    }
}
