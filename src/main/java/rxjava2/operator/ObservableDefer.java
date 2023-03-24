package rxjava2.operator;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
@Slf4j
public class ObservableDefer {
    public static void main(String[] args) throws InterruptedException {

        // defer
        Observable<LocalTime> defer = Observable.defer(
                ()->{
                    LocalTime currentTime = LocalTime.now();
                    return Observable.just(currentTime);
                }
        );

        Observable<LocalTime> observableJust = Observable.just(LocalTime.now());

        Thread.sleep(2000L);

        defer.subscribe(localTime->log.info("defer() : subscribe time1: {}",localTime)); // time1
        observableJust.subscribe(localTime ->log.info("just(): subscribe time1 {}",localTime)); // same

        Thread.sleep(2000L);

        defer.subscribe(localTime -> log.info("defer() : subscribe time2 {}",localTime)); // time1 + 2sec
        observableJust.subscribe(localTime ->log.info("just(): subscribe time2 {}",localTime)); // same
    }
}
