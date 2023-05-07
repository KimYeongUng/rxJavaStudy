package operator.err;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class ErrorEx {
    public static void main(String[] args) {
        Flux
                .range(1,5)
                .flatMap(num->{
                    if(num%3 == 0)
                        return Flux.error(new IllegalStateException("Multiple of 3 is not Allowed"));
                    else
                        return Mono.just(num*2);
                })
                .subscribe(d->log.info("#onNext: {}",d),
                        err->log.error("#Error: "+err.getMessage()));
    }
}
