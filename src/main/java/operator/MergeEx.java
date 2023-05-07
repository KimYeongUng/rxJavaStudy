package operator;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class MergeEx {
    public static void main(String[] args) throws InterruptedException {
        Flux.merge(
                    Flux.just(1,2,3,4).delayElements(Duration.ofMillis(300L)),
                    Flux.just(5,6,7).delayElements(Duration.ofMillis(500L))
                )
                .subscribe(data->log.info("#onNext:{}",data));
        Thread.sleep(2000L);
    }
}
