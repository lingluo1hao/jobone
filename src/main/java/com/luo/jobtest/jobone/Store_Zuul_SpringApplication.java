package com.usys.store.zuul;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usys.store.zuul.filter.SignVaildFilter;

@SpringBootApplication
@EnableZuulProxy
@EnableHystrix
@RestController
@MapperScan("com.usys.store.interfaces.dao")
public class Store_Zuul_SpringApplication {
	public static void main(String[] args) {
		SpringApplication.run(Store_Zuul_SpringApplication.class, args);
	}
	
	@Bean  
    public SignVaildFilter singnValidFilter(){  
        return new SignVaildFilter();
    }  
	
	@GetMapping("hello")
	public Object hello(){
		return "hello springCloud!";
	}
	
}
