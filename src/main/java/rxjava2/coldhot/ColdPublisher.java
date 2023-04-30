package rxjava2.coldhot;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@Slf4j
public class ColdPublisher {
    public static void main(String[] args) throws InterruptedException {
        Flux<String> cold =
                Flux.fromIterable(Arrays.asList("a","b","c","d"))
                        .map(String::toUpperCase);

        cold.subscribe(s->log.info("data:{}",s));
        System.out.println("==================");
        Thread.sleep(2000);
        cold.subscribe(s->log.info("data:{}",s));
    }
}
