package rxjava2.filtering;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Distinct {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("a","b","c","a","b","c","d");

        Observable.fromIterable(list)
                .distinct()
                .subscribe(data->log.info("OnNext(): {}",data));
    }
}
