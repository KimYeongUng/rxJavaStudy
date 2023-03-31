package rxjava2.error;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class onErrorResume {
    public static void main(String[] args) throws InterruptedException {
        Observable.just(5)
                .flatMap(num->Observable.interval(200L, TimeUnit.MILLISECONDS)
                        .take(5)
                        .map(i->num/i)
                        .onErrorResumeNext(
                                throwable -> {
                                    log.error("onErrorResume: "+throwable.getMessage());
                                    // publish new Observable
                                    return Observable.interval(200L,TimeUnit.MILLISECONDS)
                                            .take(5)
                                            .skip(1)
                                            .map(i->num/i);
                                }
                        ))
                .subscribe(d->log.info("onNext():{}",d));
        Thread.sleep(2000L);
    }
}
