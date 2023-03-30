package rxjava2.combine;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ObservableMerge {
    public static void main(String[] args) throws InterruptedException {
        Observable<Long> observable1 = Observable.interval(200L, TimeUnit.MICROSECONDS)
                .take(5);

        Observable<Long> observable2 = Observable.interval(400L,TimeUnit.MILLISECONDS)
                .take(5)
                .map(data->data*1000);

        Observable<String> observable3 = Observable.just("a","b","c");

        Observable.merge(observable1,observable2,observable3)
                .subscribe(data->log.info("data: {}",data));
        Thread.sleep(2000L);
    }
}
