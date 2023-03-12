package rxjava2.backpressure.strategy;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Latest {
    public static void main(String[] args) throws InterruptedException {
        log.info("start latest");
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .doOnNext(data->log.info("inteval doOnNext(): {}",data))
                .onBackpressureLatest()
                .observeOn(Schedulers.computation(),false,1)
                .subscribe(
                        data->{
                            Thread.sleep(1000L);
                            log.info("onNext(): {}",data);
                        },
                        err->log.error("onError(): {}",err.getMessage())
                );
        Thread.sleep(5500L);
    }
}
