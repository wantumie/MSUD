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
    private String packId;//捆包号
    private String packingTypeId;//包装工艺
    private String factoryProductid;//钢厂资源号
    private String spec;//规格
    private String productTypeId;//品种
    private String productTypeName;//品名
    private String shopsign;//牌号
    private String quantity;//件数
    private String machineId;//机组
    private String qualityGradeName;//质量等级
    private String confirmDate;//日期
    private String unitedPackid;//并包号
    private String confirmPerson;//检查员
    private String partId;//零件号
    private String mwrapid;//母卷号
    private String stoveNum;//炉号
    private String innerDiameter;//卷内径
    private String outDiameter;//卷外径
    private String outWidth;//卷宽度(立放时的宽度)
    private String consigneeId;//收货单位编码
    private String consigneeName;//收货单位名称
    private String consigneeRemark;//收货单位备注
}
