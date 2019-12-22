package com.carrental.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "searchExecutor")
    public Executor searchExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Bean(name = "placeOrderExecutor")
    public Executor placeOrderExecutor() {
        return new ThreadPoolTaskExecutor();
    }
}