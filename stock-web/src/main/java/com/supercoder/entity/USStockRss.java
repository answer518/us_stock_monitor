package com.supercoder.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("us_stock_rss")
public class USStockRss {

    private String id;
    private String stockCode;
    private String title;
    private String titleZh;
    private String link;
    private LocalDateTime pubDateGmt;
    private LocalDateTime pubDateBj;
    private String tags;
}
