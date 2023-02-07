package completableFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class CFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(10);

        CompletableFuture
                .supplyAsync(()->{
                    log.info("runAsync");
        //            if(true)
        //                throw new RuntimeException();
                    return 1;
                })
                .thenCompose(s->{
                    log.info("thenCompose {}",s);
                    return CompletableFuture.completedFuture(s*11);
                })
                .exceptionally(e->-100)
                .thenApplyAsync(as->{ // es threadpool
                    log.info("thenApplyAsync1 {}",as);
                    return as+5;
                },es)
                .thenAcceptAsync(s2->{
                    log.info("thenAcceptAsync2 {}",s2);
                },es);

        log.info("EXIT"); // main thread

        ForkJoinPool.commonPool().shutdown();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);
    }
}
