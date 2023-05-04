package cotext;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class ContextEx {
    public static void main(String[] args) throws InterruptedException {
        Mono
                .deferContextual(ctx->
                        Mono
                                .just("Hello "+ctx.get("firstName"))
                                .doOnNext(d->log.info("#just doOnNext: {}",d))
                        )
                .subscribeOn(Schedulers.boundedElastic())
                .publishOn(Schedulers.parallel())
                .transformDeferredContextual(
                        (mono,ctx)->mono.map(data->data+ " "+ctx.get("lastName"))
                )
                .contextWrite(context -> context.put("lastName","KIM"))
                .contextWrite(context -> context.put("firstName","YEONG UNG"))
                .subscribe(d->log.info("# onNext: {}",d));

        Thread.sleep(2000L);

    }
}
