package rxjava2.backpressure.strategy;

import io.reactivex.rxjava3.core.BackpressureOverflowStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class DropLatest {
    public static void main(String[] args) throws InterruptedException {
        log.info("start");
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .doOnNext(data->log.info("#interval doOnNext(): {}",data))
                .onBackpressureBuffer(2,()->log.error("overflow"), BackpressureOverflowStrategy.DROP_LATEST)
                .doOnNext(data->log.info("doOnNext() BackPressureBuffer: {}",data))
                .observeOn(Schedulers.computation(),false,1)
                .subscribe(
                        data->{
                            Thread.sleep(1000L);
                            log.info("OnNext() {}",data);
                        },
                        error->{log.error("onError(), {}",error.getMessage());}
                );
        Thread.sleep(2800L);
    }
}
