package rxjava2.error;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ReturnError {
    public static void main(String[] args) throws InterruptedException {
        Observable.just(5)
                .flatMap(num->Observable
                        .interval(200L, TimeUnit.MILLISECONDS)
                        .take(5)
                        .map(i->num/i)
                        .onErrorReturn(
                                ex->{
                                    if(ex instanceof ArithmeticException)
                                        log.error("Err: "+ex.getMessage());
                                    return -1L;
                                }
                        ))
                .subscribe(
                        data->{
                            if(data<0)
                                log.info("Error Received: {}",data);
                            else
                                log.info("onNext(): {}",data);
                        },
                        err->log.error("onError(): {}",err.getMessage()),
                        ()->log.info("onComplete()")

                );
        Thread.sleep(1000L);
    }
}
