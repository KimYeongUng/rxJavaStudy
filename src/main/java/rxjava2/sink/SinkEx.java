package rxjava2.sink;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.util.stream.IntStream;

@Slf4j
public class SinkEx {
    public static void main(String[] args) throws InterruptedException {
        int tasks = 6;

        Flux
                .create((FluxSink<String> sink)->{
                    IntStream
                            .range(1,tasks)
                            .forEach(n->sink.next(doTask(n)));
                })
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(n->log.info("#create(): {}",n))
                .publishOn(Schedulers.parallel())
                .map(res->res+" success")
                .doOnNext(n->log.info("#map(): {}",n))
                .publishOn(Schedulers.parallel())
                .subscribe(n->log.info("#onNext(): {}",n));
        Thread.sleep(500L);

    }

    private static String doTask(Integer n){
        return "task "+n+" result";
    }
}
