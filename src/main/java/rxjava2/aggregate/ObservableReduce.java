package rxjava2.aggregate;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObservableReduce {
    public static void main(String[] args) {
        Observable.range(1,10)
                .doOnNext(v->log.info("doOnNext: {}",v))
                .reduce((a,b)->a+b)
                .subscribe(v->log.info("onNext:(): {}",v));
    }
}
