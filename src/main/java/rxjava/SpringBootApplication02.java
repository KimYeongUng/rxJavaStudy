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
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootApplication
@EnableAsync
public class SpringBootApplication02 {

    @RestController
    public class MyController{
        Queue<DeferredResult<String>> results = new ConcurrentLinkedDeque<>();

        @Autowired
        MyService service;

        @GetMapping("/async")
        public Callable<String> async() {
            log.info("Callable");
            return ()->{
                log.info("callable Async");
                Thread.sleep(2000);
                return "Async";
            };
        }

        @GetMapping("/dr/count")
        public String drCount(){
            return String.valueOf(results.size());
        }

        @GetMapping("dr/event")
        public String drEvent(String msg){
            for (DeferredResult<String> dr : results){
                dr.setResult("Hello "+msg);
                results.remove(dr);
            }

            return "OK";
        }

        @GetMapping("/dr")
        public DeferredResult<String> deferredResult(){
            log.info("dr");
            DeferredResult<String> deferredResult = new DeferredResult<>(60000L);
            results.add(deferredResult);

            return deferredResult;
        }
        @GetMapping("/async02")
        public String callable() throws InterruptedException {
            log.info("async");
            Thread.sleep(2000);
            return "hello";
        }

        @GetMapping("/emitter")
        public ResponseBodyEmitter emitter(){
            ResponseBodyEmitter emitter = new ResponseBodyEmitter();
            Executors.newSingleThreadExecutor().submit(()->{
                try {
                    for (int i=1;i<=50;i++){
                            emitter.send("<p>Stream"+i+"</p>");
                            Thread.sleep(1000);
                    }
                }catch (Exception e){
                    log.info("EXCEPTION");
                }
            });

            return emitter;
        }
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