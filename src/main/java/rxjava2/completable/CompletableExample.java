package rxjava2.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompletableExample {
    public static void main(String[] args) throws InterruptedException {
        Completable completable = Completable.create(
                emitter -> {
                    int sum = 0;
                    for (int i=1;i<=100;i++)
                        sum+=i;
                    log.info("Sum: {}",sum);
                    emitter.onComplete();
                }
        );

        completable.subscribeOn(Schedulers.computation()).subscribe(
                ()->log.info("onComplete() "),
                err->log.error("onError(): {}",err.getMessage())
        );

        Thread.sleep(500L);
    }
}
