package rxjava2.aggregate;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Arrays;

@Slf4j
public class ObservableCount {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("a","b","c","d","e");
        Observable.range(0,10)
                .count()
                .subscribe(d->log.info("count: {}",d));
    }
}
