package com.dsmpharm.bidding;

import com.dsmpharm.bidding.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.dsmpharm.bidding.mapper")
@SpringBootApplication
public class BiddingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiddingApplication.class, args);
    }
    /**
     * 新建id生成器对象
     */
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1, 1);
    }

}
