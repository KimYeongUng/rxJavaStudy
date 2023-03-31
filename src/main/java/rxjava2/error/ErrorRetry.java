package rxjava2.error;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ErrorRetry {
    public static void main(String[] args) throws InterruptedException {
        Observable.just(5)
                .flatMap(
                        num->Observable.interval(200L, TimeUnit.MILLISECONDS)
                                .map(i->{
                                    long res;
                                    try {
                                        res = num/i;

                                    }catch (ArithmeticException e){
                                        log.error("ArithmeticException: "+e.getMessage());
                                        throw e;
                                    }
                                    return res;
                                })
                                .retry(5)
                                .onErrorReturn(throwable -> -1L)
                )
                .subscribe(
                        d->log.info("onNext(): {}",d),
                        err->log.error("onError(): "+err.getMessage()),
                        ()->log.info("onComplete()")
                );

        Thread.sleep(2000L);
    }
}
