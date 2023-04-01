package rxjava2.util;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ObervableDelay {
    public static void main(String[] args) throws InterruptedException {
        log.info("START");
        Observable.just(1,2,3,4,5)
                .delay(item->{
                    Thread.sleep(1000L);
                    return Observable.just(item);
                })
                .subscribe(d->log.info("onNext(): {}",d));

        Thread.sleep(2500L);

    }
}
