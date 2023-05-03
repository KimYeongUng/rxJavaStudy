package rxjava2.backpressure.strategy;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
public class ErrorEx {
    public static void main(String[] args) throws InterruptedException {
        Flux.interval(Duration.ofMillis(1L))
                .onBackpressureError()
                .doOnNext(d->log.info("#doOnNext(): {}",d))
                .publishOn(Schedulers.parallel())
                .subscribe(data->{
                    try {
                        Thread.sleep(5L);
                    }catch (InterruptedException e){
                        log.info("#onNext: {}",data);
                    }
                }
                ,err->log.error("#Error: {}",err.getMessage())
                );

        Thread.sleep(2000L);
    }
}
