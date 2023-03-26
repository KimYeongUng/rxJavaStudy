package rxjava2.operator.map;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObservableFlatMap {
    public static void main(String[] args) {
        Observable.just("Hello")
                .flatMap(string->Observable.just("World").map(data->string+" "+data))
                .subscribe(log::info);

        log.info("=======");

        Observable.range(2,1)
                .flatMap(num->Observable.range(1,9).map(row->num+"X"+row+"="+row*num))
                .subscribe(log::info);
    }
}
