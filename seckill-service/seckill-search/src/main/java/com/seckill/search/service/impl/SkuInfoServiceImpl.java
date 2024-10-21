package com.seckill.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.seckill.goods.feign.SkuFeign;
import com.seckill.goods.pojo.Sku;
import com.seckill.search.dao.SkuInfoMapper;
import com.seckill.search.pojo.SkuInfo;
import com.seckill.search.service.SkuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/*****
 * @Author: http://www.itheima.com
 * @Project: seckillproject
 * @Description: com.seckill.search.service.impl.SkuInfoServiceImpl
 ****/
@Service
public class SkuInfoServiceImpl implements SkuInfoService {

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private SkuFeign skuFeign;

    /***
     * 秒杀搜索列表
     * @param searchMap
     * @return
     */
    @Override
    public Page<SkuInfo> search(Map<String, String> searchMap) {
        //时间  starttime
        String starttime = searchMap.get("starttime");
        //根据bgtime实现分页搜索
        return skuInfoMapper.findByBgtime(starttime,PageRequest.of(pageConveter(searchMap)-1,20));//当前页对应的信息,bgtime
    }

    /***
     * 获取当前页->pageNum
     */
    public Integer pageConveter(Map<String, String> searchMap){
        try {
            return Integer.parseInt(searchMap.get("pageNum"));
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    /***
     * 增量操作  ->删除索引   type=3
     *           ->修改索引   type=2
     *           ->添加索引   type=1
     */
    @Override
    public void modify(Integer type, SkuInfo skuInfo) {
        if(type==1 || type==2){
            //增加-修改
            skuInfoMapper.save(skuInfo);
        }else{
            skuInfoMapper.deleteById(skuInfo.getId());
        }
    }

    /***
     * 批量导入
     */
    @Override
    public void addAll() {
        //分页数据
        int page=1,size=500;

        //1.查询总记录数
        Integer total = skuFeign.count();

        //2.根据总记录数计算总页数
        int totalpages = total%size==0? total/size : (total/size)+1;

        //3.循环总页数，查询每页的数据
        for (int i = 0; i < totalpages; i++) {
            List<Sku> skus = skuFeign.list(page, size);
            //4.将数据转换成SkuInfo
            List<SkuInfo> skuInfos = JSON.parseArray(JSON.toJSONString(skus),SkuInfo.class);

            //将开始时间转换成字符串类型
            for (SkuInfo skuInfo : skuInfos) {
                if(skuInfo.getSeckillBegin()!=null){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    skuInfo.setBgtime(simpleDateFormat.format(skuInfo.getSeckillBegin()));
                }
            }
            //批量保存
            skuInfoMapper.saveAll(skuInfos);
        }
    }
}
