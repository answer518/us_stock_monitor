package com.supercoder.test;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.junit.jupiter.api.Test;

public class RssTest {

    public static final String RSS_URL = "https://www.stocktitan.net/rss";

    public List<SyndEntry> fetchRssFeed() throws Exception {
        URI uri = URI.create(RSS_URL);
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; RSS Reader)");
        connection.setRequestProperty("Accept-Encoding", "gzip");

        InputStream raw = connection.getInputStream();
        String encoding = connection.getHeaderField("Content-Encoding");
        InputStream decoded = "gzip".equalsIgnoreCase(encoding) ? new GZIPInputStream(raw) : raw;

        try (InputStream is = decoded) {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(is));
            System.out.println(feed);
            return feed.getEntries();
        }
    }

    @Test
    public void testFetchRssFeed() throws Exception {
        List<SyndEntry> entries = this.fetchRssFeed();
        System.out.println(entries);
    }
}
