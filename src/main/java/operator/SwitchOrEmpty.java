package operator;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
public class SwitchOrEmpty {
    public static void main(String[] args) throws InterruptedException {
        log.info("#start at {}", LocalDateTime.now());
        Mono.just("Hello")
                .delayElement(Duration.ofSeconds(3))
//                .switchIfEmpty(sayDefault())
                .switchIfEmpty(Mono.defer(SwitchOrEmpty::sayDefault))
                .subscribe(data->log.info("#onNext: {}",data));
        Thread.sleep(3500);

    }

    private static Mono<String> sayDefault() {
        log.info("#Say Hi");
        return Mono.just("HI");
    }
}
