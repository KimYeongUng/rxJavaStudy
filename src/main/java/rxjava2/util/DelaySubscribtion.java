package rxjava2.util;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class DelaySubscribtion {
    public static void main(String[] args) throws InterruptedException {
        log.info("START");
        Observable.just(1,2,3,4,5)
                .doOnNext(d->log.info("doOnNext(): {}",d))
                .delaySubscription(2000L, TimeUnit.MILLISECONDS)
                .subscribe(d->log.info("sub: {}",d));
        Thread.sleep(2500L);
    }
}
