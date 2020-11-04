package com.example.demo.dao;

import com.example.demo.entity.ProductInfoMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PartPackDao {
    void insertPartPack(ProductInfoMap productInfoMap);

    void updatePartPack(ProductInfoMap productInfoMap);

    ProductInfoMap queryPartPack(String packId);

    List<ProductInfoMap> queryPartPackList(String packId);
}
