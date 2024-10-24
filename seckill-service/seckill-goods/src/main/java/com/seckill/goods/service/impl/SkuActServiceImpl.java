package com.seckill.goods.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seckill.goods.dao.ActivityMapper;
import com.seckill.goods.dao.SkuActMapper;
import com.seckill.goods.dao.SkuMapper;
import com.seckill.goods.pojo.Activity;
import com.seckill.goods.pojo.Sku;
import com.seckill.goods.pojo.SkuAct;
import com.seckill.goods.service.SkuActService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/****
 * @Author:www.itheima.com
 * @Description:SkuAct业务层接口实现类
 * @Date  0:16
 *****/
@Service
public class SkuActServiceImpl implements SkuActService {

    @Autowired
    private SkuActMapper skuActMapper;


    /**
     * 查询总数
     * @return
     */
    @Override
    public Integer count() {
        Example example = new Example(SkuAct.class);
        Example.Criteria criteria = example.createCriteria();
        //秒杀剩余商品数量>0
        criteria.andGreaterThan("seckillNum",0);
        //状态为参与秒杀，1:普通商品，2:参与秒杀
        criteria.andEqualTo("status","2");
        //秒杀结束时间>=当前时间
        criteria.andGreaterThanOrEqualTo("seckillEnd",new Date());
        return skuMapper.selectCountByExample(example);
    }

    @Override
    public List<Sku> list(Integer page, Integer size) {
        return Collections.emptyList();
    }

    /**
     * SkuAct条件+分页查询
     * @param skuAct 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<SkuAct> findPage(SkuAct skuAct, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(skuAct);
        //执行搜索
        return new PageInfo<SkuAct>(skuActMapper.selectByExample(example));
    }

    /**
     * SkuAct分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<SkuAct> findPage(int page, int size){
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<SkuAct>(skuActMapper.selectAll());
    }

    /**
     * SkuAct条件查询
     * @param skuAct
     * @return
     */
    @Override
    public List<SkuAct> findList(SkuAct skuAct){
        //构建查询条件
        Example example = createExample(skuAct);
        //根据构建的条件查询数据
        return skuActMapper.selectByExample(example);
    }


    /**
     * SkuAct构建查询对象
     * @param skuAct
     * @return
     */
    public Example createExample(SkuAct skuAct){
        Example example=new Example(SkuAct.class);
        Example.Criteria criteria = example.createCriteria();
        if(skuAct!=null){
            // 
            if(!StringUtils.isEmpty(skuAct.getSkuId())){
                    criteria.andEqualTo("skuId",skuAct.getSkuId());
            }
            // 
            if(!StringUtils.isEmpty(skuAct.getActivityId())){
                    criteria.andEqualTo("activityId",skuAct.getActivityId());
            }
        }
        return example;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id){
        skuActMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改SkuAct
     * @param skuAct
     */
    @Override
    public void update(SkuAct skuAct){
        skuActMapper.updateByPrimaryKey(skuAct);
    }

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private SkuMapper skuMapper;

    /**
     * 增加SkuAct
     * @param skuAct
     */
    @Override
    public void add(SkuAct skuAct){
        //查询活动
        Activity activity = activityMapper.selectByPrimaryKey(skuAct.getActivityId());

        //修改每个Sku的信息
        for (Sku sku : skuAct.getSkus()) {
            sku.setSeckillBegin(activity.getBegintime());
            sku.setSeckillEnd(activity.getEndtime());
            sku.setStatus("2"); //1普通商品，2秒杀商品
            skuMapper.updateByPrimaryKeySelective(sku);

            //增加关联信息
            SkuAct newSkuAct = new SkuAct();
            newSkuAct.setActivityId(skuAct.getActivityId());
            newSkuAct.setSkuId(sku.getId());
            skuActMapper.insertSelective(newSkuAct);
        }
    }

    /**
     * 根据ID查询SkuAct
     * @param id
     * @return
     */
    @Override
    public SkuAct findById(String id){
        return  skuActMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询SkuAct全部数据
     * @return
     */
    @Override
    public List<SkuAct> findAll() {
        return skuActMapper.selectAll();
    }
}
