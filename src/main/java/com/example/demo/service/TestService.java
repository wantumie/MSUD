package com.example.demo.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TestService {

    String testService(String id);

    String getUserName(String id) throws Exception;

    String postUserName(String id);

    List<Map> queryAllList();

    PageInfo<Map> queryPartList(String partId , Integer pageNo, Integer pageSize);

    List<Map> queryPartinfo(String partId);

    void updatePartinfo(String partId, String spec);

    void insertPartinfo(String partId, String spec);

    String wsService();

    String testJson();
}
