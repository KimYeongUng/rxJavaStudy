package rxjava;

import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

@SpringBootApplication
@SuppressWarnings("deprecation")
public class SpringBootApplication03 {

    @RestController
    public static class MyController{
        AsyncRestTemplate rc = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(
                1)));

        @Autowired
        Myservice myservice;

        @GetMapping("/rest")
        public DeferredResult<String> rest(int idx) {

            DeferredResult<String> dr = new DeferredResult<>();

            ListenableFuture<ResponseEntity<String>> f1 = rc.getForEntity("http://localhost:8081/service1?req={req}",
                    String.class,"hello"+idx);
            f1.addCallback(s->{
                ListenableFuture<ResponseEntity<String>> f2 = rc.getForEntity("http://localhost:8081/service2?req={req}",
                        String.class,s.getBody());

                f2.addCallback(s2->{
                    ListenableFuture<String> f3 = myservice.work(s2.getBody());
                    f3.addCallback(s3->{
                        dr.setResult(s3);
                    },e->{
                        dr.setErrorResult(e.getMessage());
                    });
                },e->{
                    dr.setErrorResult(e.getMessage());
                });
            }, e->{
                dr.setErrorResult(e.getMessage());
            });

            return dr;
        }
    }

    @Service
    public static class Myservice{
        public ListenableFuture<String> work(String req){
            return new AsyncResult<>(req+"/service");
        }
    }

    @Bean
    public ThreadPoolTaskExecutor myThreadPool(){
        ThreadPoolTaskExecutor tp = new ThreadPoolTaskExecutor();
        tp.setCorePoolSize(1);
        tp.setMaxPoolSize(10);
        tp.initialize();
        return tp;
    }

    public static void main(String[] args) {
        SpringApplication.run(RemoteService.class,args);
    }
}