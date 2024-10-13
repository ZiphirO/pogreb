package com.ziphiro.pogreb.fileStorage.config;

import com.ziphiro.pogreb.util.StrVal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@EnableAsync
public class ApplicationConfig {

    @Bean
    public WebMvcConfigurer corsConfig(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

    @Bean(name = "asyncDownload")
    public ThreadPoolTaskExecutor downloadExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("downloadThread-");
        executor.setRejectedExecutionHandler((r, executor1) -> log.warn(StrVal.THREAD_POOL_IS_FULL.getValue()));
        executor.initialize();
        return executor;
    }

    @Bean(name = "asyncUpload")
    public ThreadPoolTaskExecutor uploadExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("uploadThread-");
        executor.setRejectedExecutionHandler((r, executor1) -> log.warn(StrVal.THREAD_POOL_IS_FULL.getValue()));
        executor.initialize();
        return executor;
    }
}
