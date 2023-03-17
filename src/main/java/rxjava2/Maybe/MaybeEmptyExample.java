package rxjava2.Maybe;

import io.reactivex.rxjava3.core.Maybe;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MaybeEmptyExample {
    public static void main(String[] args) {
        Maybe.empty()
                .subscribe(
                        data->log.info("onSuccess(): {}",data),
                        err->log.error("onError(): {}",err.getMessage()),
                        ()->log.info("onComplete()")
                );
    }
}
