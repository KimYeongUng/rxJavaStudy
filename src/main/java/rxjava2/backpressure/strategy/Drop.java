package rxjava2.backpressure.strategy;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Drop {
    public static void main(String[] args) throws InterruptedException {
        log.info("Start Drop Strategy");
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .doOnNext(data->log.info("doOnNext() interval: {}",data))
                .onBackpressureDrop(drop->log.info("Drop: {}",drop))
                .observeOn(Schedulers.computation(),false,1)
                .subscribe(
                        data->{
                            Thread.sleep(1000L);
                            log.info("onNext(): {}",data);
                        },
                        error->log.error("onError(): {}",error.getMessage())
                );
        Thread.sleep(5500L);
    }
}
