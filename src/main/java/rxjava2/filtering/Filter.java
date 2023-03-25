package rxjava2.filtering;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class Filter {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("a","b","c","ab","bc","cc");
                Observable.fromIterable(list)
                        .filter(data->data.startsWith("a"))
                .subscribe(data->log.info("onNext():{}",data));
    }
}
