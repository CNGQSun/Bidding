package com.dsmpharm.bidding;

import com.dsmpharm.bidding.utils.IdWorker;
import com.dsmpharm.bidding.utils.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.dsmpharm.bidding.mapper")
@SpringBootApplication
//第⑤步
//public class BiddingApplication extends SpringBootServletInitializer {
//    /**
//     * 为打war包重写configure
//     * @param application
//     * @return
//     */
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(BiddingApplication.class);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(BiddingApplication.class, args);
//    }
//    /**
//     * 新建id生成器对象
//     */
//    @Bean
//    public IdWorker idWorker(){
//        return new IdWorker(1, 1);
//    }
//    /**
//     * 新建JWT对象
//     */
//    @Bean
//    public JwtUtil getJwtUtil(){
//        return new JwtUtil();
//    }
//
//}
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
    /**
     * 新建JWT对象
     */
    @Bean
    public JwtUtil getJwtUtil(){
        return new JwtUtil();
    }

}
