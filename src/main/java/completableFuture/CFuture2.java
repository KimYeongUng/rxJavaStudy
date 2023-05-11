package completableFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
public class CFuture2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Supplier parameter
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> "Hello");
        String res=  cf.get();
        System.out.println(res);

        // Runnable parameter
        CompletableFuture.runAsync(()->log.info("runAsync"));

        System.out.println("============================");

        // chaining
        CompletableFuture.supplyAsync(()->{
            // ForkJoinPool.commonPool() , 비동기로 값 넘김
            log.info("supplyAsync");
            return "Hello";
        }).thenApplyAsync(s->{
            log.info("then: {}",s);
            return s+" World";
        }).thenAcceptAsync(s->{
            log.info("res: {}",s);
        });

        Thread.sleep(2000L);
    }
}
