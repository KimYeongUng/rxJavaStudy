package rxjava2.operator;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ObservableInterval {
    public static void main(String[] args) throws InterruptedException {
        Observable.interval(0,1000L, TimeUnit.MILLISECONDS)
                .map(num->num+" Count")
                .subscribe(data -> log.info("operate: "+data));
        Thread.sleep(3000);
    }
}
