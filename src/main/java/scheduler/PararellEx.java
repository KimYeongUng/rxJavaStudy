package scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;


@Slf4j
public class PararellEx {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[]{2,3,4,5,6,7,12,344,232,111,5323,553,25,958})
                .parallel()
                .runOn(Schedulers.parallel())
                .subscribe(d->log.info("OnNext: {}",d));

        Thread.sleep(2000L);
    }
}
