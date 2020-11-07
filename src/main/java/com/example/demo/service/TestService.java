package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.ProductInfoMap;
import com.example.demo.entity.R;

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

    void insertPartinfo(String partId, String spec);

    String wsService();

    String testJson();
}
