package rxjava2.backpressure;


import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class BackPressure {
    public static void main(String[] args) throws InterruptedException {
        // buffer default size : 128
        Flowable.interval(1L, TimeUnit.MILLISECONDS)
                .doOnNext(data->log.info("doOnNext() {}",data))
                .observeOn(Schedulers.computation())
                .subscribe(
                        data->{
                            log.info("print #waiting processing...");
                            Thread.sleep(1000L);
                            log.info("OnNext() {}",data);
                        },
                        error->{log.info("onError() {}",error.getMessage());},
                        ()->log.info("onComplete")
                );
        Thread.sleep(2000);
    }
}
