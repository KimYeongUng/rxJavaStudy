package rxjava2.operator.map;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class ObservableConcatMap {
    public static void main(String[] args) {
        long start  = System.currentTimeMillis();
        AtomicLong end = new AtomicLong();
        Observable.interval(100L, TimeUnit.MILLISECONDS)
                .take(4)
                .skip(2)
                .concatMap(
                        num-> Observable.interval(200L,TimeUnit.MILLISECONDS)
                                .take(10)
                                .skip(1)
                                .map(row->num+" X "+row+" = "+num*row)
                ).subscribe(
                        data->log.info("onNext(): {}",data), err->log.error(err.getMessage()),()->{
                            end.set(System.currentTimeMillis()-start);
                            log.info("time: "+end);
                        }
                );
    }
}
