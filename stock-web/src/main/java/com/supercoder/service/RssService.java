package com.supercoder.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndEntry;

@Service
public interface RssService {

    public void handleRssFeed() throws Exception;
    /**
     * 从RSS feed中获取股票信息
     * @return 股票信息列表
     * @throws Exception 如果获取RSS feed失败
     */
    public List<SyndEntry> fetchRssFeed(String rssUrl) throws Exception;

}
