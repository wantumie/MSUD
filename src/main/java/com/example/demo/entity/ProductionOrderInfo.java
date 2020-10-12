package com.example.demo.entity;

import lombok.Data;

/**
 * describe
 *
 * @Auther Mr.Garfield
 * @Date 2020/10/3
 */

@Data
public class ProductionOrderInfo {

    private String segNo;//地区公司代码
    private String productionOrderCode;//工单号
    private String fProductId;//投料资源号
    private String fPackId;//投料捆包号

}
