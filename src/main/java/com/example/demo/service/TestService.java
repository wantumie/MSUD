package com.example.demo.service;

import java.util.List;
import java.util.Map;

public interface TestService {

    String testService(String id);

    String getUserName(String id) throws Exception;

    String postUserName(String id);

    List<Map> queryAllList();

    String queryDetail(String segNo, String productionOrderCode);

    String queryProduct(String segNo, String productionOrderCode, String fProductId, String fPackId);

    String queryPartPackInfo(String packId);
}
