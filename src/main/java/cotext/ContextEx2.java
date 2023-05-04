package cotext;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

@Slf4j
public class ContextEx2 {
    public static void main(String[] args) throws InterruptedException {
        final String key1 ="company";
        final String key2 ="firstName";
        final String key3 ="lastName";

        Mono
                .deferContextual(ctx->Mono.just(ctx.get(key1)+","+ctx.get(key2)+","+ctx.get(key3)))
                .publishOn(Schedulers.parallel())
                .contextWrite(context -> context.putAll(Context.of(key2,"Hero",key3,"Kim").readOnly())
                )
                .publishOn(Schedulers.parallel())
                .contextWrite(context -> context.put(key1,"myCompany"))
                .subscribe(d->log.info("#onNext: {}",d));
        Thread.sleep(2000L);

    }
}
