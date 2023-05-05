package operator;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class OperatorEx {
    public static void main(String[] args) throws InterruptedException {
        List<String> iter = Arrays.asList("a","b","c");


        // JustorEmpty
        Mono.justOrEmpty(null)
                .subscribe(
                        data->{},
                        err->log.info("#err: {}",err.getMessage()),
                        ()->log.info("#onComplete")
                );

        System.out.println("-------------------");

        // fromIterable
        Flux.fromIterable(iter)
                .subscribe(d->log.info("#data: {}",d));

        System.out.println("-------------------");

        //fromStream
        Flux
                .fromStream(iter.stream())
                .filter(data->data.equals("a"))
                .subscribe(data->log.info("#data: {}",data));

        System.out.println("-------------------");

        //Range
        Flux.range(1,10)
                .filter(data->data>=5)
                .subscribe(data->log.info("#data: {}",data));

        System.out.println("-------------------");

        //Defer
        log.info("#start : {}", LocalTime.now());
        Mono<LocalDateTime> justMono = Mono.just(LocalDateTime.now());
        Mono<LocalDateTime> deferMono = Mono.defer(()->Mono.just(LocalDateTime.now()));
        Thread.sleep(2000L);
        justMono.subscribe(data->log.info("#justMono just1: {}",data));
        deferMono.subscribe(data->log.info("#onNext defer1: {}",data));
        Thread.sleep(2000L);
        justMono.subscribe(data->log.info("#justMono just2: {}",data));
        deferMono.subscribe(data->log.info("#onNext defer2: {}",data));
    }
}
