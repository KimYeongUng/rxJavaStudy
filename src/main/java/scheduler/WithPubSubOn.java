package scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class WithPubSubOn {
    public static void main(String[] args) {
        Flux
                .fromArray(new Integer[]{1,2,3,4,5,6})
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(d->log.info("#doOnNext: {}",d))
                .filter(data-> data %2 != 0)
                .doOnNext(d->log.info("doOnNext filter: {}",d))
                .publishOn(Schedulers.parallel())
                .map(data->data*10)
                .doOnNext(data->log.info("#doOnNext map:{}",data))
                .subscribe(data->log.info("#OnNext: {}",data));

    }
}
