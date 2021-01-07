package com.example.demo.dao;

import com.example.demo.entity.ProductInfoMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PartPackDao {
    void insertPartPack(ProductInfoMap productInfoMap);

    void updatePartPack(ProductInfoMap productInfoMap);

    ProductInfoMap queryPartPack(@Param("packId")String packId);

    List<ProductInfoMap> queryPartPackList(@Param("packId")String packId);

    Integer isRepeat(ProductInfoMap productInfoMap);

    List<String> getArmNum();

    List<String> getSerialNum(String armNum);

}
