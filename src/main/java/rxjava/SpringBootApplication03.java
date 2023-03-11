package rxjava;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

@SpringBootApplication
@SuppressWarnings("deprecation")
@Slf4j
@EnableAsync
public class SpringBootApplication03 {

    @RestController
    public static class MyController{
        AsyncRestTemplate rt = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));
        static final String URL1 = "http://localhost:8081/service1?req={req}";
        static final String URL2 = "http://localhost:8081/service2?req={req}";

        @Autowired
        Myservice myservice;

        WebClient client = WebClient.create();

        @GetMapping("/rest")
        public Mono<String> rest(int idx) {
            return client.get().uri(URL1,idx).exchange()
                    .flatMap(c->c.bodyToMono(String.class))
                    .doOnNext(log::info)
                    .flatMap((String res1)-> client.get().uri(URL2,res1).exchange())
                    .flatMap(c->c.bodyToMono(String.class))
                    .doOnNext(log::info)
                    .flatMap(res2->Mono.fromCompletionStage(myservice.work(res2)))
                    .doOnNext(log::info);
        }
    }

    public static class AcceptCompletion<S,T> extends Completion<S,Void>{
        public Consumer<S> con;
        AcceptCompletion(Consumer<S> con){
            this.con = con;
        }

        @Override
        void run(S value){
            con.accept(value);
        }
    }

    public static class ApplyCompletion<S,T> extends Completion<S,T>{
        public Function<S,ListenableFuture<T>> fn;

        public ApplyCompletion(Function<S,ListenableFuture<T>> fn){
            this.fn = fn;
        }

        @Override
        void run(S value){
            ListenableFuture<T> lf = fn.apply(value);
            lf.addCallback(this::complete, this::error);
        }
    }

    public static class ErrorCompletion<T> extends Completion<T,T> {
        public Consumer<Throwable> econ;
        public ErrorCompletion(Consumer<Throwable> econ) {
            this.econ = econ;
        }

        @Override
        void run(T value){
            if(next != null)
                next.run(value);
        }

        @Override
        void error(Throwable e){
            econ.accept(e);
        }

    }

    public static class Completion<S,T>{
        Completion next;
        Consumer<ResponseEntity<String>> con;
        Completion(){}
        public Completion(Consumer<ResponseEntity<String>> con) {
            this.con = con;
        }

        Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn;
        public Completion(Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn) {
            this.fn = fn;
        }

        public static <S,T> Completion<S,T> from(ListenableFuture<T> lf) {
            Completion<S,T> c = new Completion<>();
            lf.addCallback(c::complete, c::error);

            return c;
        }

        void error(Throwable e) {
            if(next != null)
                next.error(e);
        }

        void complete(T s) {
            if(next != null)
                next.run(s);
        }

        void run(S value) {
        }

        public void andAccept(Consumer<T> con){
            Completion<T,Void> c = new AcceptCompletion(con);
            this.next = c;
        }

        public Completion andError(Consumer<Throwable> econ){
            Completion<T,T> c = new ErrorCompletion(econ);
            this.next = c;
            return c;
        }

        public <V>Completion andApply(Function<S,ListenableFuture<V>> con) {
            Completion c = new ApplyCompletion(con);
            this.next = c;
            return c;
        }


    }

    @Service
    public static class Myservice{
        @Async
        public CompletableFuture<String> work(String req){
            return CompletableFuture.completedFuture("/async/"+req);
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
        System.setProperty("reactor.ipc.netty.workerCount","1");
        System.setProperty("reactor.ipc.netty.pool.maxConnections","1000");
        SpringApplication.run(RemoteService.class,args);
    }
}