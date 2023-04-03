package rxjava2.util;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ObservableTimeout {
    public static void main(String[] args) throws InterruptedException {
        Observable.range(1,5)
                .map(data->{
                    Long timeout = 1000L;
                    if(data == 4)
                        timeout = 2000L;
                    Thread.sleep(timeout);
                    return data;
                })
                .timeout(1200L, TimeUnit.MILLISECONDS)
                .subscribe(
                        d->log.info("data: {}",d),
                        err->log.error("err: {}",err.getMessage()),
                        ()->log.info("onComplete()")
                );

        Thread.sleep(5000L);
    }
}
