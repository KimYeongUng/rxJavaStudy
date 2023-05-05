package cotext;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class ContextEx4 {
    public static void main(String[] args) {
        final String key1 = "company";

        Mono<String> mono = Mono.deferContextual(ctx->
                Mono.just("Company: "+ctx.get(key1))
        )
                .publishOn(Schedulers.parallel());

        mono.contextWrite(context -> context.put(key1,"Apple"))
                .subscribe(data->log.info("#sub1 onNext: {}",data));

        mono.contextWrite(context -> context.put(key1,"Google"))
                .subscribe(data->log.info("#sub2 onNext: {}",data));
    }
}
