package com.supercoder.utils;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Stock Titan 新闻爬虫工具类
 * <p>
 * 目标页面 https://www.stocktitan.net/news/live.html 启用了浏览器 JS 验证，
 * 普通 HTTP 请求会被拦截，因此默认通过同源的 RSS Feed（https://www.stocktitan.net/rss）获取新闻数据，
 * 并从标题中提取股票代码作为标签。
 */
public class StockTitanCrawler {

    public static final String LIVE_URL = "https://www.stocktitan.net/news/live.html";
    public static final String RSS_URL = "https://www.stocktitan.net/rss";

    public static void main(String[] args) throws Exception {
        try {
            System.out.println("Start");
            String title = "AAPL Stock News";
            List<String> tags = getTags(title);
            System.out.println(tags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getTags(String title) throws Exception {
        Document doc = Jsoup.connect(LIVE_URL).userAgent("Mozilla/5.0 (compatible; RSS Reader)").timeout(10000).get();
        return extractTagsByTitle(doc, title);
    }

    /**
     * 从新闻标题中提取股票代码标签
     *
     * @param title 新闻标题
     * @return 股票代码，如 "AAPL"；无法提取时返回 null
     */
    public static List<String> extractTagsByTitle(Document doc, String title) {
        List<String> result = new ArrayList<>();
        
        Elements titleElements = doc.select("a.feed-link");

        for (Element titleEl : titleElements) {
            if (title.equals(titleEl.text())) {

                // 2. 向上找到整个 news-row
                Element newsRow = titleEl.closest("div.news-row");

                if (newsRow == null) {
                    continue;
                }

                // 3. 在该 news-row 中找 tags
                Elements tagElements = newsRow.select("div[name=tags] span.badge");

                for (Element tag : tagElements) {
                    result.add(tag.text().trim());
                }

                break;
            }
        }

        return result;
    }
}
