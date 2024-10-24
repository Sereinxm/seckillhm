package com.seckill;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;
/*****
 * @Author: http://www.itheima.com
 * @Project: seckill
 * @Description: com.seckill.GoodsApplication


 ****/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.seckill.user.dao")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }

}
