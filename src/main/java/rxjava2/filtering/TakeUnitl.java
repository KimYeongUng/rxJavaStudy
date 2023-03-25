package rxjava2.filtering;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TakeUnitl {
    public static void main(String[] args) throws InterruptedException {
        Observable.range(1,15)
                .takeUntil(data->data == 5)
                .subscribe(d->log.info("data: {}",d));
        Thread.sleep(2000L);
    }
}
