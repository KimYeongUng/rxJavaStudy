package rxjava2.operator;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class FromIterable {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("a","b","c","d");
        Observable.fromIterable(list)
                .subscribe(s -> log.info("onNext(): {}",s));

    }
}
