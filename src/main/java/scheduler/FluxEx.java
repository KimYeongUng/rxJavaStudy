package scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FluxEx {
    public static void main(String[] args) throws InterruptedException {
//        Flux.range(1,10)
//                .publishOn(Schedulers.newSingle("pubOn-"))
//                .log()
//                .subscribeOn(Schedulers.newSingle("subOn-"))
//                .subscribe(System.out::println);

        Flux.interval(Duration.ofMillis(200)) // 200ms 마다 하나씩 생성
                .take(10) // 10개 받고 끝
                .subscribe(s->log.info("OnNext: {}",s)); // subscribe function

        TimeUnit.SECONDS.sleep(10);
        log.info("EXIT");
    }
}
