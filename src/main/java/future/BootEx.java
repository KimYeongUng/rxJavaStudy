package future;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Future;

@SpringBootApplication
@Slf4j
@EnableAsync
public class BootEx {

    @Service
    static class MyService{
        @Async
        public ListenableFuture<String> hello() throws InterruptedException {
            log.info("hello()");
            Thread.sleep(1000L);
            return new AsyncResult<>("HELLO");
        }
    }

    @Autowired MyService myService;

    public static void main(String[] args) {
        try {
            ConfigurableApplicationContext c = SpringApplication.run(BootEx.class,args);
        }catch (Exception ignored){
        }
    }

    @Bean
    ApplicationRunner run(){
        return args -> {
            log.info("run");
            ListenableFuture<String> res = myService.hello();
            res.addCallback(s->log.info("success: "+s), e->log.error(e.getMessage()));
            log.info("Exit");
        };
    }
}
