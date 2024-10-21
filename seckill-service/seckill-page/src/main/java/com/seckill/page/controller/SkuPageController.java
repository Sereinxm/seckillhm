package com.seckill.page.controller;

import com.seckill.goods.pojo.Sku;
import com.seckill.page.service.SkuPageService;
import com.seckill.util.Result;
import com.seckill.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/*****
 * @Author: http://www.itheima.com
 * @Project: seckillproject
 * @Description: com.seckill.page.controller.SkuPageController
 ****/
@RestController
@RequestMapping(value = "/page")
@CrossOrigin
public class SkuPageController {

    @Autowired
    private SkuPageService skuPageService;

    @Value("${htmlPath}")
    private String htmlPath;


    /****
     * 删除商品详情页
     */
    @DeleteMapping(value = "/html/{id}")
    public Result delHtml(@PathVariable(value = "id")String id){
        String fileName = id+".html";
        skuPageService.delHtml(fileName,htmlPath);
        return new Result(true,StatusCode.OK,"删除静态页成功！");
    }


    /****
     * 静态页生成
     */
    @PostMapping(value = "/html")
    public Result html(@RequestBody Sku sku) throws Exception {
        //数据模型
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("path",htmlPath);
        dataMap.put("templateName","items.ftl");
        dataMap.put("name",sku.getId()+".html");//id.html
        dataMap.put("address","北京");
        dataMap.put("sku",sku);

        //生成静态页
        skuPageService.itemPage(dataMap);
        return new Result(true, StatusCode.OK,"生成静态页成功！");
    }
}
