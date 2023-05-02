package com.xiaomi.controller;

import com.github.pagehelper.PageInfo;
import com.xiaomi.pojo.ProductInfo;
import com.xiaomi.service.ProductInfoService;
import com.xiaomi.utils.FileNameUtil;
import com.xiaomi.vo.ProductInfoVo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Author: Lu
 * @Date: 2023/4/30 18:44
 * @Description:
 */
@Controller
@RequestMapping("/prod")
public class ProductInfoController {
    //保存的文件名
    private String saveFileName = "";

    @Autowired
    private ProductInfoService productInfoService;

    //显示全部商品不分页
    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        List<ProductInfo> infos = productInfoService.getAll();
        request.setAttribute("list",infos);
        return "product";
    }

    //实现按条件查询 未分页的
    @ResponseBody  //返回值用的
    @RequestMapping("/condition")
    public void condition(ProductInfoVo vo,HttpSession session){
        List<ProductInfo> list = productInfoService.selectCondition(vo);
        session.setAttribute("list",list);
    }

    //显示第一页的记录
    @RequestMapping("/split")
    public String split(HttpServletRequest request){
        PageInfo<ProductInfo> pageInfo = null;
        Object vo = request.getSession().getAttribute("vo");
        if(vo!=null){
            pageInfo = productInfoService.splitPageVo((ProductInfoVo) vo,5);
        }else{
            pageInfo = productInfoService.splitPage(1,5);
        }
        request.getSession().setAttribute("info",pageInfo);
        return "product";
    }

    //ajax的分页处理 嵌入多条件查询
    @ResponseBody  //能够处理ajax请求，并且绕过视图解析器
    @RequestMapping("/ajaxsplit")
    public void ajaxsplit(ProductInfoVo vo, HttpSession session){
        PageInfo<ProductInfo> info = productInfoService.splitPageVo(vo, 5);
        session.setAttribute("info",info);
        session.setAttribute("vo",vo);
    }

    @ResponseBody //处理ajax请求
    @RequestMapping("/ajaxImg")  //pimage与input的name相同
    public String ajaxImg(MultipartFile pimage,HttpServletRequest request) throws IOException {
        //提取文件名+后缀
        saveFileName = FileNameUtil.getUUIDFileName() + FileNameUtil.getFileType(pimage.getOriginalFilename());

        //得到项目中存储的路径
        String path = request.getServletContext().getRealPath("/image_big");

        //转存
        pimage.transferTo(new File(path+File.separator+saveFileName));

        //返回客户端的JSON对象，封装图片路径，为了实现页面立即回显
        JSONObject obejct = new JSONObject();
        obejct.put("imgurl",saveFileName);
        //返回json格式的对象
        return obejct.toString();
    }

    //新增商品
    @RequestMapping("/save")
    public String save(ProductInfo productInfo,HttpServletRequest request){
        productInfo.setpImage(saveFileName);
        productInfo.setpDate(new Date());

        //新增
        int save = productInfoService.save(productInfo);
        //save大于0插入成功
        if(save>0){
            request.setAttribute("msg","添加成功");
        }else{
            request.setAttribute("msg","添加失败");
        }
        //清空文件名
        saveFileName = "";
        //刷新数据库
        return "forward:/prod/split.action";
    }

    @RequestMapping("/getProd")
    public String getProd(ProductInfoVo vo,int pid, HttpServletRequest request){
        //通过id查询商品信息
        ProductInfo productInfo = productInfoService.getById(pid);
        //存储到页面中
        request.setAttribute("prod",productInfo);
        //将vo对象放在session域中进行回显
        request.getSession().setAttribute("vo",vo);
        //跳转到update页面
        return "update";
    }

    @RequestMapping("/update")
    public String update(ProductInfo info,@RequestParam(required = false) Integer page,HttpServletRequest request){

        if(page==null){
            page=1;
        }
        //因为ajax的异步上传，如果有上传过，则saveFileName里面是上传来的名称
        //如果没有使用过Ajax的异步上传，则saveFileName=""
        //实体类info使用表单隐藏于提供上来的pImage名称
        if(!saveFileName.equals("")){
            info.setpImage(saveFileName);
        }
        int update = productInfoService.update(info);
        //save大于0插入成功
        if(update>0){
            request.setAttribute("msg","更新成功");
        }else{
            request.setAttribute("msg","更新失败");
        }
        //清空fileName，防止对下一次更新产生影响
        saveFileName = "";
        return "forward:/prod/split.action";
    }

    @RequestMapping("/delete")
    public String delete(ProductInfoVo vo,int pid,HttpServletRequest request){
        int delete = productInfoService.delete(pid);
        //save大于0插入成功
        if(delete>0){
            request.setAttribute("msg","删除成功");
            //查询成功将vo对象放在session中传递过去
            request.getSession().setAttribute("deleteVo",vo);
        }else{
            request.setAttribute("msg","删除失败");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit", produces = "text/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request){
        PageInfo<ProductInfo> info = null;
        Object deleteVo = request.getSession().getAttribute("deleteVo");
        if(deleteVo!=null){
            info = productInfoService.splitPageVo((ProductInfoVo) deleteVo,5);
        }else{
            info = productInfoService.splitPage(1, 5);
        }
        //将info信息保存到session作用域，供页面使用
        request.getSession().setAttribute("info",info);
        //将删除状态的信息取出返回
        return request.getAttribute("msg");
    }

    //实现删除批量商品
    @RequestMapping("deleteBatch")
    //pids="1,2,3,4" ps=[1,2,3,4]
    public String deleteBatch(String str,int page,HttpServletRequest request){
        String[] ps = str.split(",");
        int result = -1;
        try {
            result = productInfoService.deleteBatch(ps);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(result>0){
            request.setAttribute("msg","成功删除"+result+"条数据");

        }else{
            request.setAttribute("msg","批量删除失败");
        }

        //return回去进行分页
        return "forward:deleteAjaxSplit.action?page"+page;
    }




}