package com.xiaomi.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaomi.mapper.ProductInfoMapper;
import com.xiaomi.pojo.ProductInfo;
import com.xiaomi.pojo.ProductInfoExample;
import com.xiaomi.service.ProductInfoService;
import com.xiaomi.vo.ProductInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Lu
 * @Date: 2023/4/30 18:41
 * @Description:
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    //数据访问层对象
    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> getAll() {
        //查询所有的product并返回集合
        return productInfoMapper.selectByExample(new ProductInfoExample());
    }

    //select * from product_info limit 起始记录条数=((当前页-1)*每页取几条),每页取几条
    @Override
    public PageInfo<ProductInfo> splitPage(int pageNum, int pageSize) {
        //分页插件使用PageHelper工具类来完成分页设置
        PageHelper.startPage(pageNum,pageSize);

        //进行PageInfo的数据封装
        //进行有条件的查询操作，需要创建条件查询对象
        ProductInfoExample example = new ProductInfoExample();

        //设置排序，按主键降序排序（使新插入的数据在最前面）
        //在select标签中，如果OrderByClause不为空则在后面添加p_id desc
        example.setOrderByClause("p_id desc");

        //排完序后，得到集合
        List<ProductInfo> list = productInfoMapper.selectByExample(example);

        //将查到的集合封装到PageInfo中
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list,3);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo productInfo) {
        return productInfoMapper.insert(productInfo);
    }

    @Override
    public ProductInfo getById(int pid) {
        return productInfoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public int update(ProductInfo productInfo) {
        return productInfoMapper.updateByPrimaryKey(productInfo);
    }

    @Override
    public int delete(int pid) {
        return productInfoMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return productInfoMapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo productInfoVo) {
        return productInfoMapper.selectCondition(productInfoVo);
    }

    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo productInfoVo,int splitPageNum){

        //开启分页功能
        PageHelper.startPage(productInfoVo.getPage(),splitPageNum);
        List<ProductInfo> infos = productInfoMapper.selectCondition(productInfoVo);
        return  new PageInfo<>(infos,3);
    }

}