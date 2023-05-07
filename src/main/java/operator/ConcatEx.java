package operator;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class ConcatEx {
    public static void main(String[] args) {
        Flux.concat(Flux.just(1,2,3), Flux.just(4,5,6))
                .subscribe(data->log.info("#onNext: {}",data));
    }
}
