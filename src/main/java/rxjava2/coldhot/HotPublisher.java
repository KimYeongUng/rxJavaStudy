package rxjava2.coldhot;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class HotPublisher {
    public static void main(String[] args) throws InterruptedException {
        String[] data = {"a","b","c","d"};

        Flux<String> flux = Flux.fromArray(data)
                .delayElements(Duration.ofSeconds(1))
                .share();

        flux.subscribe(d->log.info("subscriber1: {}",d)); // subscriber1

        Thread.sleep(2000);

        flux.subscribe(d->log.info("subscriber2: {}",d)); // subscriber2

        Thread.sleep(3000);
        log.info("EXIT");

    }
}
