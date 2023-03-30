package rxjava2.combine;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ObservableConcat {
    public static void main(String[] args) throws InterruptedException {
        Observable<String> observable = Observable.just("a","b","c");
        Observable<Long> observable1 = Observable.interval(300L, TimeUnit.MILLISECONDS).take(5);

        Observable.concat(observable1,observable)
                .subscribe(data->log.info("data: {}",data));
        Thread.sleep(3000L);
    }
}
