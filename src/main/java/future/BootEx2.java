package future;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;


@Slf4j
@SpringBootApplication
public class BootEx2 {

    @RestController
    @SuppressWarnings("deprecation")
    static class MyContorller{
        private static final String URL = "http://localhost:8081/service?req={req}";
        public static final String URL2 = "http://localhost:8081/service2?req={req}";
        AsyncRestTemplate restTemplate = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(
                new NioEventLoopGroup(1)
        ));
        @Autowired Myservice myservice;

        @GetMapping("/rest")
        public DeferredResult<String> rest(Integer idx){
            log.info("hello");
            DeferredResult<String> dr = new DeferredResult<>();
            ListenableFuture<ResponseEntity<String>> f1 = restTemplate.getForEntity(
                    URL,String.class,"hello "+idx
            );
            f1.addCallback(s->{
                ListenableFuture<ResponseEntity<String>> f2 = restTemplate.getForEntity(
                        URL2,String.class,s.getBody()
                );
                f2.addCallback(s2->{
                    ListenableFuture<String> f3 = myservice.work(s2.getBody());
                    f3.addCallback(s3->{
                        dr.setResult(s3);
                    },ex3 -> {
                        dr.setErrorResult(ex3.getMessage());
                    });
                },ex2 -> {
                    dr.setErrorResult(ex2.getMessage());
                });
            },ex -> {
                dr.setErrorResult(ex.getMessage());
            });

            return dr;
        }
    }

    @Service
    public static class Myservice{
        @Async
        public ListenableFuture<String> work(String req){

            return new AsyncResult<>(req+" /async");
        }
    }

    @Bean
    public ThreadPoolTaskExecutor myThreadPool(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(1);
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    public static void main(String[] args) {
        SpringApplication.run(BootServiceEx.class,args);
    }
}
