package rxjava2.Maybe;

import io.reactivex.rxjava3.core.Maybe;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class MaybeExample {
    public static void main(String[] args) {
        Maybe<String> maybe = Maybe.create(emitter -> {
            log.info("onSuccess(): "+new Date());
        });

        maybe.subscribe(
                data->log.info("onSuccess(): "+data),
                err->log.error("onError(): {}",err.getMessage()),
                ()->log.info("onComplete() ")
        );
    }
}
