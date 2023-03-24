package rxjava2.operator.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static rxjava2.operator.future.FutureSync.doTask;

@Slf4j
public class FutureAsync {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long startTime = System.currentTimeMillis();
        log.info("START");
        Future<Integer> future = getFutureTask(5);
        int n = doTask(2,1);
        log.info("Task Finished : task"+n);
        n = doTask(3,1);
        log.info("Task Finished : task"+n);
        future.get();

        long endTime = System.currentTimeMillis();

        double execute = (endTime-startTime)/1000.0;
        log.info("Process Time: "+execute+"sec");
    }

    private static Future<Integer> getFutureTask(int sec){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = (Future<Integer>) executor.submit(()->{
            try {
                Thread.sleep(sec*1000L);
                log.info("Process End");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        executor.shutdown();
        return future;
    }
}
