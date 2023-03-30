package rxjava2.combine;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ObservableZip {
    public static void main(String[] args) throws InterruptedException {
        Observable<Long> observable = Observable.interval(200L, TimeUnit.MILLISECONDS)
                .take(5);

        Observable<String> observable1 = Observable.just("a","b","c");

        Observable.zip(observable,observable1,(data1,data2)->data1+data2)
                .subscribe(data->log.info("data:{}",data));

        Thread.sleep(2000L);

    }
}
