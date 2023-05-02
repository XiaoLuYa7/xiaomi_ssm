package com.xiaomi.service;

import com.github.pagehelper.PageInfo;
import com.xiaomi.pojo.ProductInfo;
import com.xiaomi.vo.ProductInfoVo;

import java.util.List;

/**
 * @Author: Lu
 * @Date: 2023/4/30 18:40
 * @Description:
 */
public interface ProductInfoService {
    //查询所有产品信息
    List<ProductInfo> getAll();

    //查询分页数据
    PageInfo<ProductInfo> splitPage(int pageNum,int pageSize);

    //新增商品
    int save(ProductInfo productInfo);

    //编辑商品
    ProductInfo getById(int pid);

    //更新商品
    int update(ProductInfo productInfo);

    //删除单个商品
    int delete(int pid);

    //批量删除的功能
    int deleteBatch(String[] ids);

    //根据条件查询
    List<ProductInfo> selectCondition(ProductInfoVo productInfoVo);

    //多条件分页查询
    PageInfo<ProductInfo> splitPageVo(ProductInfoVo productInfoVo,int splitPageNum);
}