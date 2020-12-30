package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.ProductInfoMap;
import com.example.demo.entity.R;
import com.github.pagehelper.PageInfo;

import java.text.ParseException;
import java.util.List;

public interface PartPackService {
    String queryDetail(String segNo, String productionOrderCode) throws ParseException;

    String queryProduct(String segNo, String productionOrderCode, String fProductId, String fPackId);

    String queryPartPackInfo(String packId);

    PageInfo<ProductInfoMap> queryPartPackInfoList(String packId, int pageNo, int pageSize);

    String updatePartinfo(ProductInfoMap productInfoMap) throws Exception;

    String insertPartinfo(ProductInfoMap productInfoMap) throws Exception;
}
