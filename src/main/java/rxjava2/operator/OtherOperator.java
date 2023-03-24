package rxjava2.operator;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class OtherOperator {
    public static void main(String[] args) throws InterruptedException {
        // range
        log.info("===RANGE===");
        Observable<Integer> range = Observable.range(0,5);
        range.subscribe(num->log.info("OnNext(): {}",num));


        // timer
        log.info("===TIMER===");
        Observable<String> timer = Observable.timer(2000, TimeUnit.MILLISECONDS)
                .map(cnt ->"do Work");
        timer.subscribe(data->log.info("other Thread:{}",data));
        Thread.sleep(2000L);
    }
}
