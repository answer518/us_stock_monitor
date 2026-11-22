package com.supercoder.service.impl;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;
import java.util.zip.GZIPInputStream;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.supercoder.entity.USStockMsg;
import com.supercoder.entity.USStockRss;
import com.supercoder.enums.StockEnum;
import com.supercoder.service.RssService;
import com.supercoder.service.StockService;
import com.supercoder.utils.StockTitanCrawler;
import com.supercoder.utils.TimeZoneUtils;

import jakarta.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class RssServiceImpl implements RssService {

    public static final String RSS_URL = "https://www.stocktitan.net/rss";

    @Resource
    private StockService stockService;

    @Override
    public void handleRssFeed() throws Exception {
        List<SyndEntry> rssList = this.fetchRssFeed(RSS_URL);
        if (rssList == null || rssList.isEmpty()) {
            System.out.println("No RSS feed found.");
            return;
        }
        for (SyndEntry entry : rssList) {
            USStockRss usStockRss = new USStockRss();
            usStockRss.setStockCode(getStockCode(entry.getTitle()));
            usStockRss.setTitle(getStockTitle(entry.getTitle()));

            usStockRss.setLink(entry.getLink());
            usStockRss.setPubDateGmt(TimeZoneUtils.dateToGmt(entry.getPublishedDate()));
            usStockRss.setPubDateBj(TimeZoneUtils.dateToBj(entry.getPublishedDate()));
            
            if (stockService.isStockNewsExist(usStockRss.getStockCode(), usStockRss.getLink())) {
                System.out.println("Stock news already exists: " + usStockRss.toString());
                continue;
            }

            usStockRss.setTitleZh("");
            try {
                List<String> tags = StockTitanCrawler.getTags(entry.getTitle());
                usStockRss.setTags(StockEnum.getTagsZh(tags));
            } catch (Exception e) {
                usStockRss.setTags("");
            }
            
            stockService.saveStockNews(usStockRss);

            USStockMsg stockMsg = new USStockMsg();
            BeanUtils.copyProperties(usStockRss, stockMsg);

            stockMsg.setPubDateBj(usStockRss.getPubDateBj().toString());
            stockService.getStockUnusualCount(usStockRss, null, null);
        }
    }

    @Override
    public List<SyndEntry> fetchRssFeed(String rssUrl) throws Exception {
        URI uri = URI.create(rssUrl);
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; RSS Reader)");
        connection.setRequestProperty("Accept-Encoding", "gzip");

        InputStream raw = connection.getInputStream();
        String encoding = connection.getHeaderField("Content-Encoding");
        InputStream decoded = "gzip".equalsIgnoreCase(encoding) ? new GZIPInputStream(raw) : raw;

        try (InputStream is = decoded) {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(is));
            return feed.getEntries();
        }
    }

    private String getStockTitle(String title) {
        String[] titleArr = title.split("\\|");
        return titleArr[0].trim();
    }

    private String getStockCode(String title) {
        String[] titleArr = title.split("\\|");
        String stockStr = titleArr[titleArr.length - 1];
        return stockStr.split("Stock News")[0].trim();
    }
}
