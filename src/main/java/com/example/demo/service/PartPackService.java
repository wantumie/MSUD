package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.ProductInfoMap;
import com.example.demo.entity.R;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PartPackService {
    R queryDetail(String segNo, String productionOrderCode);

    JSONObject queryProduct(String segNo, String productionOrderCode, String fProductId, String fPackId);

    String queryPartPackInfo(String packId);

    PageInfo<ProductInfoMap> queryPartPackInfoList(String packId, int pageNo, int pageSize);

    void updatePartinfo(ProductInfoMap productInfoMap);

    void insertPartinfo(ProductInfoMap productInfoMap);
}
