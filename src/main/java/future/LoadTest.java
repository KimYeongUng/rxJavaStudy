package future;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

// 부하테스트
@Slf4j
public class LoadTest {
    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        AtomicInteger counter = new AtomicInteger(0);
        ExecutorService es = Executors.newFixedThreadPool(100);

        RestTemplate rt = new RestTemplate();
        String url = "http://localhost:8081/service?req={req}";
        CyclicBarrier barrier = new CyclicBarrier(101);



        for (int i=0;i<100;i++){
            es.submit(()->{
                int idx = counter.addAndGet(1);
                barrier.await();
                log.info("Thread {}",idx);
                StopWatch sw = new StopWatch();
                sw.start();
                String res = rt.getForObject(url,String.class);
                sw.stop();
                log.info("Elapsed: "+idx+" -> "+sw.getTotalTimeMillis()+"ms"+" "+res);
                return null;
            });
        }

        barrier.await();
        StopWatch watch = new StopWatch();
        watch.start();

        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);
        watch.stop();
        log.info("Total: "+watch.getTotalTimeSeconds());
    }
}
