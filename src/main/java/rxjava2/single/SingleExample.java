package rxjava2.single;

import io.reactivex.rxjava3.core.Single;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class SingleExample {
    public static void main(String[] args) {
        Single<String> single = Single.create(emitter -> emitter.onSuccess("Success: "+new Date()));

        single.subscribe(
                data->log.info("onSuccess(): {}",data),
                err->log.error("onError(): {}",err.getMessage())
        );
    }
}
