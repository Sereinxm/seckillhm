package com.seckill.goods.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seckill.goods.dao.ActivityMapper;
import com.seckill.goods.dao.SeckillTimeMapper;
import com.seckill.goods.pojo.Activity;
import com.seckill.goods.pojo.SeckillTime;
import com.seckill.goods.service.ActivityService;
import com.seckill.util.IdWorker;
import com.seckill.util.TimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/****
 * @Author:www.itheima.com
 * @Description:Activity业务层接口实现类
 * @Date  0:16
 *****/
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private SeckillTimeMapper seckillTimeMapper;

    @Autowired
    private IdWorker idWorker;

    /**
     * Activity条件+分页查询
     * @param activity 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Activity> findPage(Activity activity, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(activity);
        //执行搜索
        return new PageInfo<Activity>(activityMapper.selectByExample(example));
    }

    /**
     * Activity分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Activity> findPage(int page, int size){
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<Activity>(activityMapper.selectAll());
    }

    /**
     * Activity条件查询
     * @param activity
     * @return
     */
    @Override
    public List<Activity> findList(Activity activity){
        //构建查询条件
        Example example = createExample(activity);
        //根据构建的条件查询数据
        return activityMapper.selectByExample(example);
    }


    /**
     * Activity构建查询对象
     * @param activity
     * @return
     */
    public Example createExample(Activity activity){
        Example example=new Example(Activity.class);
        Example.Criteria criteria = example.createCriteria();
        if(activity!=null){
            // 
            if(!StringUtils.isEmpty(activity.getId())){
                    criteria.andEqualTo("id",activity.getId());
            }
            // 
            if(!StringUtils.isEmpty(activity.getName())){
                    criteria.andLike("name","%"+activity.getName()+"%");
            }
            // 状态：1开启，2未开启
            if(!StringUtils.isEmpty(activity.getStatus())){
                    criteria.andEqualTo("status",activity.getStatus());
            }
            // 
            if(!StringUtils.isEmpty(activity.getStartdate())){
                    criteria.andEqualTo("startdate",activity.getStartdate());
            }
            // 开始时间，单位：时分秒
            if(!StringUtils.isEmpty(activity.getBegintime())){
                    //criteria.andEqualTo("begintime",activity.getBegintime());
                    criteria.andGreaterThanOrEqualTo("begintime",activity.getBegintime());
            }
            // 结束时间，单位：时分秒
            if(!StringUtils.isEmpty(activity.getEndtime())){
                    //criteria.andEqualTo("endtime",activity.getEndtime());
                    criteria.andLessThanOrEqualTo("endtime",activity.getEndtime());
            }
            // 
            if(!StringUtils.isEmpty(activity.getTotalTime())){
                    criteria.andEqualTo("totalTime",activity.getTotalTime());
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
        Activity activity = new Activity();
        activity.setIsDel(2);
        activity.setId(id);
        activityMapper.updateByPrimaryKeySelective(activity);
    }

    /**
     * 修改Activity
     * @param activity
     */
    @Override
    public void update(Activity activity){
        //添加
        activityMapper.updateByPrimaryKeySelective(activity);
    }


    /**
     * 增加Activity
     * @param activity
     */
    @Override
    public void add(Activity activity){
        //选中的id集合
        List<Integer> seckillIds = activity.getSeckillIds();

        //循环添加活动到数据库中
        for (Integer seckillId : seckillIds) {
            Activity oneActivity = new Activity();
            BeanUtils.copyProperties(activity,oneActivity);
            //查询当前活动对应的信息
            SeckillTime seckillTime = seckillTimeMapper.selectByPrimaryKey(seckillId);
            oneActivity.setId("A"+idWorker.nextId());
            oneActivity.setBegintime(seckillTime.getStarttime());
            oneActivity.setEndtime(seckillTime.getEndtime());
            float times = TimeUtil.dif2hour(oneActivity.getBegintime(), oneActivity.getEndtime());
            oneActivity.setTotalTime(times);
            //添加
            activityMapper.insertSelective(oneActivity);
        }
    }

    /**
     * 根据ID查询Activity
     * @param id
     * @return
     */
    @Override
    public Activity findById(String id){
        return  activityMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Activity全部数据
     * @return
     */
    @Override
    public List<Activity> findAll() {
        return activityMapper.selectAll();
    }
}
