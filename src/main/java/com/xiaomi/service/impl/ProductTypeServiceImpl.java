package com.xiaomi.service.impl;

import com.xiaomi.mapper.ProductTypeMapper;
import com.xiaomi.pojo.ProductType;
import com.xiaomi.pojo.ProductTypeExample;
import com.xiaomi.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Lu
 * @Date: 2023/5/1 15:55
 * @Description:
 */
@Service("productTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Override
    public List<ProductType> getAll() {
        return productTypeMapper.selectByExample(new ProductTypeExample());
    }
}