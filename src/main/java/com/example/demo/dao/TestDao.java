package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TestDao {
    String queryName(String id);
    List<Map> queryAllList();
}
