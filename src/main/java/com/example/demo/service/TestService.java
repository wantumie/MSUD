package com.example.demo.service;

import com.example.demo.entity.ProductInfoMap;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public interface TestService {

    String testService(String id);

    String getUserName(String id) throws Exception;

    String postUserName(String id);

    List<Map> queryAllList();

    List<Map> queryPartList(String partId);

    List<Map> queryPartinfo(String partId);

    void updatePartinfo(String partId, String spec);



    String queryDetail(String segNo, String productionOrderCode);

    String queryProduct(String segNo, String productionOrderCode, String fProductId, String fPackId);

    String queryPartPackInfo(String packId);

    List<ProductInfoMap> queryPartPackInfoList(String packId);


    void insertPartinfo(String partId, String spec);
}
