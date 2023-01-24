package future;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// 부하테스트
@Slf4j
public class LoadTest {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        ExecutorService es = Executors.newFixedThreadPool(100);

        RestTemplate rt = new RestTemplate();
        String url = "http://localhost:8080/async";
        StopWatch watch = new StopWatch();
        watch.start();

        for (int i=0;i<100;i++){
            es.execute(()->{
                int idx = counter.addAndGet(1);
                log.info("Thread "+idx);
                StopWatch sw = new StopWatch();
                sw.start();
                rt.getForObject(url,String.class);
                sw.stop();
                log.info("Elapsed: "+idx+"->"+sw.getTotalTimeMillis());
            });
        }

        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);
        watch.stop();
        log.info("Total: "+watch.getTotalTimeSeconds());
    }
}