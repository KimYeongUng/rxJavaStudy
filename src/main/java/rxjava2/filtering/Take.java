package rxjava2.filtering;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Take {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("a","b","c","d","e");

        Observable.fromIterable(list)
                .take(3)
                .subscribe(d->log.info("onNext(): {}",d));

    }
}
