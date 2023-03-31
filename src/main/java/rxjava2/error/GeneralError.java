package rxjava2.error;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class GeneralError {
    public static void main(String[] args) throws InterruptedException {
        Observable.just(5)
                .flatMap(n->Observable
                        .interval(200L, TimeUnit.MILLISECONDS)
                        .doOnNext(d->log.info("onNext: {}",d))
                        .take(5)
                        .map(i->n/i)).subscribe(
                                data->log.info("onNext():{}",data),
                                err->log.info("onError(): {}",err.getMessage()),
                        ()->log.info("onComplete()")
                );
        Thread.sleep(2000L);
    }
}
