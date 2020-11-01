package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * describe
 *
 * @Auther Mr.Garfield
 * @Date 2020/6/2
 */

@Data
public class rmPartPack {

    private Number id;
    private String partId;
    private String chargePlat;
    private String packId;
    private String packQty;
    private String putinQty;
    private String putinWeight;
    private String mwrapId;
    private String factoryProductId;
    private String qualityGradeName;
    private String shopsign;


}
