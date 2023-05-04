package scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class WithoutPubSubOn {
    // processing main thread
    public static void main(String[] args) {
        Flux
                .fromArray(new Integer[]{1,3,5,7,9})
                .doOnNext(d->log.info("#doOnNext fromArr: {}",d))
                .filter(data-> data > 1)
                .doOnNext(d->log.info("#doOnNext filter: {}",d))
                .map(d->d+1)
                .doOnNext(d->log.info("#doOnNext map: {}",d))
                .subscribe(data->log.info("#onNext: {}",data));
    }
}
