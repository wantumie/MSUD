package com.example.demo.dao;

import com.example.demo.entity.ProductInfoMap;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PartPackDao {
    void insertPartPack(ProductInfoMap productInfoMap);

    void updatePartPack(ProductInfoMap productInfoMap);

    ProductInfoMap queryPartPack(String packId);
}
