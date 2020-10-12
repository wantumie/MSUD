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



    private String packingTypeId;

    private String factoryProductid;

    private String spec;
    private String productTypeName;
    private String shopsign;

    private String quantity;
    private String machineId;
    private String qualityGradeName;



    private String confirmDate;
    private String unitedPackid;

    private String confirmPerson;
    private String partid;
    private String mwrapid;
    private String grossWeight;
    private String putinWeight;
    private String netWeight;
}
