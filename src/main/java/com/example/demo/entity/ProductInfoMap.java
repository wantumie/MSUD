package com.example.demo.entity;

import lombok.Data;

/**
 * describe
 *
 * @Auther Mr.Garfield
 * @Date 2020/10/3
 */

@Data
public class ProductInfoMap {

    private String packid;



    private String packingTypeId;//包装工艺

    private String factoryProductid;//钢厂资源号

    private String spec;//规格
    private String productTypeName;//品名
    private String shopsign;//牌号

    private String quantity;//件数
    private String machineId;//机组
    private String qualityGradeName;//质量等级



    private String confirmDate;//日期
    private String unitedPackid;//并包号

    private String confirmPerson;//检验员
    private String partid;
    private String mwrapid;
    private String grossWeight;
    private String putinWeight;
    private String netWeight;
}
