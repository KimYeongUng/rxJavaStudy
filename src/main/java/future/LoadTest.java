package future;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class LoadTest {
    static AtomicInteger atomic = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        ExecutorService es = Executors.newFixedThreadPool(100);
        RestTemplate template = new RestTemplate();
        String url = "http://localhost:8080/rest";

        CyclicBarrier cyclicBarrier = new CyclicBarrier(101);
        StopWatch watch = new StopWatch();
        watch.start();

        for (int i=0;i<100;i++){
            es.submit(()->{
                int cnt = atomic.addAndGet(1);
                cyclicBarrier.await();
                log.info("thread-"+cnt);

                StopWatch sw = new StopWatch();
                sw.start();
                template.getForObject(url,String.class);
                sw.stop();

                String res = template.getForObject(url,String.class,cnt);

                log.info("Elapsed: {} {} {}",cnt,sw.getTotalTimeSeconds()+"sec",res);
                return null; // callable - cyclicbarrier
            });
        }

        cyclicBarrier.await();
        es.shutdown();
        watch.stop();

        log.info("totalTime: {}",watch.getTotalTimeSeconds());
    }
}
