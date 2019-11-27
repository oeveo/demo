package com.springboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.Async;

@SpringBootApplication
@ImportResource(locations = { "classpath:druid-bean.xml" })
@Async
/** 开启缓存 */
@EnableCaching
/**
 * DemoApplication class
 *
 * @author wk
 * @date 2019/8/15
 */
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
