package rxjava2.operator.map;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class ObservableMap {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

        Observable.fromIterable(list)
                .filter(num->num>5)
                .map(num->num*10)
                .subscribe(d->log.info("result: {}",d));
    }
}
