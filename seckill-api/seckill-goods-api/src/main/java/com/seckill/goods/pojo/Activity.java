package com.seckill.goods.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/****
 * @Author:www.itheima.com
 * @Description:Activity构建
 * @Date  19:13
 *****/
@Table(name="tb_activity")
public class Activity implements Serializable{

	@Id
    @Column(name = "id")
	private String id;//

    @Column(name = "name")
	private String name;//

    @Column(name = "status")
	private Integer status;//状态：1开启，2未开启

	@JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "startdate")
	private Date startdate;//

	@JsonFormat(pattern = "HH:mm:ss")
    @Column(name = "begintime")
	private Date begintime;//开始时间，单位：时分秒

	@JsonFormat(pattern = "HH:mm:ss")
    @Column(name = "endtime")
	private Date endtime;//结束时间，单位：时分秒

    @Column(name = "total_time")
	private Float totalTime;//

	@Column(name = "is_del")
	private Integer isDel;//

	//记录当前勾选的活动时间集合
	private List<Integer> seckillIds;

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public List<Integer> getSeckillIds() {
		return seckillIds;
	}

	public void setSeckillIds(List<Integer> seckillIds) {
		this.seckillIds = seckillIds;
	}

	//get方法
	public String getId() {
		return id;
	}

	//set方法
	public void setId(String id) {
		this.id = id;
	}
	//get方法
	public String getName() {
		return name;
	}

	//set方法
	public void setName(String name) {
		this.name = name;
	}
	//get方法
	public Integer getStatus() {
		return status;
	}

	//set方法
	public void setStatus(Integer status) {
		this.status = status;
	}
	//get方法
	public Date getStartdate() {
		return startdate;
	}

	//set方法
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	//get方法
	public Date getBegintime() {
		return begintime;
	}

	//set方法
	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}
	//get方法
	public Date getEndtime() {
		return endtime;
	}

	//set方法
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Float getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Float totalTime) {
		this.totalTime = totalTime;
	}
}
