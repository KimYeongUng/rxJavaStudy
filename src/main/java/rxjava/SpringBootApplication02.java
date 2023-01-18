package rxjava;

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
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Slf4j
@SpringBootApplication
@EnableAsync
public class SpringBootApplication02 {

    @Autowired MyService service;

    @Component
    public static class MyService{
        @Async
        public Future<String> hello() throws InterruptedException {
            log.info("hello()");
            Thread.sleep(2000);
            return new AsyncResult<>("Hello Async");
        }
    }

    @Bean
    public ApplicationRunner run(){
        return args -> {
            log.info("run()");
            Future<String> res = service.hello();
            log.info("exit: "+res.isDone());
            log.info("result: "+res.get());
        };
    }

    public static void main(String[] args) {
        try (ConfigurableApplicationContext c = SpringApplication.run(SpringBootApplication02.class, args)) {

        }
    }


}
