package scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FluxEx {
    public static void main(String[] args) throws InterruptedException {
        Flux.interval(Duration.ofMillis(200L))
                .take(10)
                .subscribe(l->log.info("onNext(): {}",l));
        TimeUnit.SECONDS.sleep(10);
        log.info("EXIT");
    }
}
