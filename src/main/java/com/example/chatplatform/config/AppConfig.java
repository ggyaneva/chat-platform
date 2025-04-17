package com.example.chatplatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AppConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // Number of threads to keep active
        executor.setMaxPoolSize(20); // Maximum number of threads
        executor.setQueueCapacity(50); // Tasks queue capacity
        executor.setThreadNamePrefix("AsyncTask-");
        executor.initialize();
        return executor;
    }
}