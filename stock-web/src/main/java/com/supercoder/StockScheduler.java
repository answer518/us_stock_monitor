package com.supercoder;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.supercoder.service.RssService;

import jakarta.annotation.Resource;

@Component
@EnableScheduling
public class StockScheduler {

    @Resource
    private RssService rssService;
    // 定时抓取股票信息
    // 常见的cron表达式：每5分钟执行一次任务
    // 0/5 0 0 * * *
    @Scheduled(cron = "*/60 * * * * ?")
    public void getStockInfo() throws Exception {
        rssService.handleRssFeed();
    }
}
