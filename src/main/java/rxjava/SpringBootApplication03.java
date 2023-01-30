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

import java.util.function.Consumer;
import java.util.function.Function;

@SpringBootApplication
@SuppressWarnings("deprecation")
public class SpringBootApplication03 {

    @RestController
    public static class MyController{
        AsyncRestTemplate rt = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(
                1)));
        static final String URL1 = "http://localhost:8081/service1?req={req}";
        static final String URL2 = "http://localhost:8081/service2?req={req}";
        @Autowired
        Myservice myservice;

        @GetMapping("/rest")
        public DeferredResult<String> rest(int idx) {

            DeferredResult<String> dr = new DeferredResult<>();

            // chaining
            Completion
                    .from(rt.getForEntity(URL1,String.class,"h"+idx))
                    .andApply(s->rt.getForEntity(URL2,String.class,s.getBody()))
                    .andError(e->dr.setErrorResult(e.toString()))
                    .andAccept(s->dr.setResult(s.getBody()));
            return dr;
        }
    }

    public static class AcceptCompletion extends Completion{
        public Consumer<ResponseEntity<String>> con;
        AcceptCompletion(Consumer<ResponseEntity<String>> con){
            this.con = con;
        }

        @Override
        void run(ResponseEntity<String> value){
            con.accept(value);
        }
    }

    public static class AsyncCompletion extends Completion{
        public Function<ResponseEntity<String>,ListenableFuture<ResponseEntity<String>> > fn;

        public AsyncCompletion(Function<ResponseEntity<String>,ListenableFuture<ResponseEntity<String>>> fn){
            this.fn = fn;
        }

        @Override
        void run(ResponseEntity<String> value){
            ListenableFuture<ResponseEntity<String>> lf = fn.apply(value);
            lf.addCallback(this::complete, this::error);
            con.accept(value);
        }
    }

    public static class ErrorCompletion extends Completion {
        public Consumer<Throwable> econ;
        public ErrorCompletion(Consumer<Throwable> econ) {
            this.econ = econ;
        }

        @Override
        void run(ResponseEntity<String> value){
            if(next != null)
                next.run(value);
        }

        @Override
        void error(Throwable e){
            econ.accept(e);
        }

    }

    public static class Completion{
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

        public static Completion from(ListenableFuture<ResponseEntity<String>> lf) {
            Completion c = new Completion();
            lf.addCallback(c::complete, c::error);
            return c;
        }

        void error(Throwable e) {
            if(next != null)
                next.error(e);
        }

        void complete(ResponseEntity<String> s) {
            if(next != null)
                next.run(s);
        }

        void run(ResponseEntity<String> value) {
            if(con != null)
                con.accept(value);
            else if(fn != null){
                ListenableFuture<ResponseEntity<String>> lf = fn.apply(value);
                lf.addCallback(this::complete, this::error);
            }

        }

        public void andAccept(Consumer<ResponseEntity<String>> con){
            Completion c = new AcceptCompletion(con);
            this.next = c;
        }

        public Completion andError(Consumer<Throwable> econ){
            Completion c = new ErrorCompletion(econ);
            this.next = c;
            return c;
        }

        public Completion andApply(Function<ResponseEntity<String>,ListenableFuture<ResponseEntity<String>>> con) {
            Completion c = new AsyncCompletion(con);
            this.next = c;
            return c;
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