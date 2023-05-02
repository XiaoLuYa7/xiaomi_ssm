package com.xiaomi.service;

import com.xiaomi.pojo.ProductType;

import java.util.List;

/**
 * @Author: Lu
 * @Date: 2023/5/1 15:48
 * @Description:
 */
public interface ProductTypeService {
    //查询所有类别
    List<ProductType> getAll();
}
