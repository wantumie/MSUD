package com.example.demo.dao;

import com.example.demo.entity.ProductInfoMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TestDao {

    String queryName(String id);

    List<Map> queryAllList();

    ProductInfoMap queryPartinfo(String packId);

    List<Map> queryPartList();
}
