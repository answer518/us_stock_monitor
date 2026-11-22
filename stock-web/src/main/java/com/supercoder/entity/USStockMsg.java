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
public class USStockMsg {

    private String stockCode;
    private String titleZh;
    private String link;
    private String pubDateBj;
    private String tags;
    private Integer counts24Hour;
    private Integer counts3Day;
    private Integer counts1Week;
}
