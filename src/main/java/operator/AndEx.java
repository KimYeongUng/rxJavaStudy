package operator;


import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
public class AndEx {
    public static void main(String[] args) throws InterruptedException{
        restartApplicationServer().and(
                restartDBServer()
        )
                .subscribe(
                        data->log.info("#onNext: {}",data),
                        err->log.error("err: "+err.getMessage()),
                        ()->log.info("#OnComplete")
                );
        Thread.sleep(5000L);
    }

    private static Publisher<String> restartDBServer() {
        return Mono.just("DB Server was restarted Successfully")
                .delayElement(Duration.ofSeconds(2))
                .doOnNext(log::info);
    }

    private static Mono<String> restartApplicationServer(){
        return Mono.just("Application Server was restarted Successfully")
                .delayElement(Duration.ofSeconds(4))
                .doOnNext(log::info);
    }
}
