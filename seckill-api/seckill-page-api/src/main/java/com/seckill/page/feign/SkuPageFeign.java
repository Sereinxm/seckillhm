package com.seckill.page.feign;

import com.seckill.goods.pojo.Sku;
import com.seckill.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/*****
 * @Author: http://www.itheima.com
 * @Project: seckillproject
 * @Description: com.seckill.page.feign.SkuPageFeign
 ****/
@FeignClient(name = "seckill-page")
public interface SkuPageFeign {

    /****
     * 删除商品详情页
     */
    @DeleteMapping(value = "/page/html/{id}")
    Result delHtml(@PathVariable(value = "id")String id);

    /****
     * 静态页生成
     */
    @PostMapping(value = "/page/html")
    Result html(@RequestBody Sku sku) throws Exception;
}
