package rxjava2.filtering;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Skip {
    public static void main(String[] args) throws InterruptedException {
        Observable.range(1,15)
                .skip(3)
                .subscribe(d->log.info("onNext(): {}",d));

        log.info("Skip by Time");
        Observable.interval(100L, TimeUnit.MILLISECONDS)
                .skip(1000L,TimeUnit.MILLISECONDS)
                .subscribe(d->log.info("onNext(): {}",d));
        Thread.sleep(3000L);
    }
}
