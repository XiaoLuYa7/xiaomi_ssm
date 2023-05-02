package com.xiaomi.listener;

import com.xiaomi.pojo.ProductType;
import com.xiaomi.service.ProductInfoService;
import com.xiaomi.service.ProductTypeService;
import com.xiaomi.service.impl.ProductInfoServiceImpl;
import com.xiaomi.service.impl.ProductTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * @Author: Lu
 * @Date: 2023/5/1 15:59
 * @Description:
 */
@WebListener
public class ProductTypeListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //手动从Spring容器中取出ProductTypeServiceImpl的对象
        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ProductTypeService productTypeService = (ProductTypeService) ioc.getBean("productTypeServiceImpl");
        //得到所有商品类别
        List<ProductType> typeList = productTypeService.getAll();
        //将该集合放到应用域中
        sce.getServletContext().setAttribute("typeList",typeList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}