package rxjava;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@Slf4j
@SpringBootApplication
@EnableAsync
public class SpringBootApplication02 {

    @RestController
    public class MyController{

        @Autowired
        MyService service;

        @GetMapping("/async")
        public Callable<String> async() throws InterruptedException {
            log.info("Callable"); // thread 8080-exec-1
            return ()->{
                log.info("callable Async"); // thread task -1
                Thread.sleep(2000);
                return "Async";
            };
        }

//        @GetMapping("/async02")
//        public String callable() throws InterruptedException {
//            log.info("async");
//            Thread.sleep(2000);
//            return "hello";
//        }


    }

    @Component
    public static class MyService{
        @Async // asynchronized
        public ListenableFuture<String> hello() throws InterruptedException {
            log.info("hello()");
            Thread.sleep(2000);
            return new AsyncResult<>("Hello Async");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication02.class,args);
    }

}
