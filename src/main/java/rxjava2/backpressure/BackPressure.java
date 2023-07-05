package rxjava2.backpressure;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;


@Slf4j
public class BackPressure {
    public static void main(String[] args) throws InterruptedException {
        Flux.range(1,5)
                .doOnRequest(data->log.info("# doOnRequest: {}",data))
                .subscribe(new BaseSubscriber<>() {
                    @Override
                    protected void hookOnSubscribe(@NotNull Subscription subscription) {
                        request(1);
                    }

                    @SneakyThrows
                    @Override
                    protected void hookOnNext(@NotNull Integer value) {
                        Thread.sleep(2000);
                        log.info("# hookOnNext: {}", value);
                        request(1);
                    }
                });
    }
}
