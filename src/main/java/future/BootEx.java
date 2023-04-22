package future;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@SpringBootApplication
@Slf4j
@EnableAsync
public class BootEx {

    @RestController
    static class MyController{
        @GetMapping("/async")
        public Callable<String> async() throws InterruptedException {
            log.info("callable");
            return ()->{
                log.info("async");
                Thread.sleep(2000L);
                return "Hello";
            };
        }

        @GetMapping("/sync")
        public String callable() throws InterruptedException {
            log.info("sync");
            Thread.sleep(2000L);
            return "hello";
        }
    }

    @Component
    static class MyService{
        @Async
        public ListenableFuture<String> hello() throws InterruptedException {
            log.info("hello()");
            Thread.sleep(1000L);
            return new AsyncResult<>("HELLO");
        }
    }


    public static void main(String[] args) {
        ConfigurableApplicationContext c = SpringApplication.run(BootEx.class,args);
    }

    @Bean
    ThreadPoolTaskExecutor tp(){
        ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
        te.setCorePoolSize(10);
        te.setMaxPoolSize(100);
        te.setQueueCapacity(50);
        te.setThreadNamePrefix("ThReAd");
        te.initialize();
        return te;
    }
}
