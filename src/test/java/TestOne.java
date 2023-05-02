import com.xiaomi.mapper.ProductInfoMapper;
import com.xiaomi.pojo.ProductInfo;
import com.xiaomi.vo.ProductInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author: Lu
 * @Date: 2023/5/2 15:04
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml","classpath:applicationContext_service.xml"})
public class TestOne {
    @Autowired
    ProductInfoMapper productInfoMapper;

    @Test
    public void testConditionSelect(){
        List<ProductInfo> productInfos = productInfoMapper.selectCondition(
                new ProductInfoVo("", null, 1000, 2000,0));
        productInfos.forEach(System.out::println);
    }
}