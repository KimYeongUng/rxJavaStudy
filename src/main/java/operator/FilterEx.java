package operator;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FilterEx {
    public static void main(String[] args) {
        Flux.range(1,10)
                .skip(2)
                .filter(d->d%2!=0)
                .subscribe(data->log.info("#onNext: {}",data));
    }
}
