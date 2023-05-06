package operator;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class MapEx {
    public static void main(String[] args) {
        Flux.just("HI","HELLO","BYE")
                .map(String::toLowerCase)
                .subscribe(d->log.info("#onNext: {}",d));
    }
}
