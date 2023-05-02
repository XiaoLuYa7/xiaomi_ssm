package com.xiaomi.mapper;

import com.xiaomi.pojo.ProductInfo;
import com.xiaomi.pojo.ProductInfoExample;
import com.xiaomi.vo.ProductInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductInfoMapper {
    int countByExample(ProductInfoExample example);

    int deleteByExample(ProductInfoExample example);

    int deleteByPrimaryKey(Integer pId);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    List<ProductInfo> selectByExample(ProductInfoExample example);

    ProductInfo selectByPrimaryKey(Integer pId);

    int updateByExampleSelective(@Param("record") ProductInfo record, @Param("example") ProductInfoExample example);

    int updateByExample(@Param("record") ProductInfo record, @Param("example") ProductInfoExample example);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);

    //实现批量删除的功能
    int deleteBatch(String[] ids);

    //多条件查询商品
    List<ProductInfo> selectCondition(ProductInfoVo productInfoVo);
}