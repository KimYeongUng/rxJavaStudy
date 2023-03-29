package rxjava2.tomap;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ObservableToMap {
    public static void main(String[] args) {
        Single<Map<String,String>> single
                = Observable.just("Korea-Seoul","Japan-Tokyo","China-Beijing","USA-Wasington D.C")
                .toMap(key->key.split("-")[0],value->value.split("-")[1]);

        single.subscribe(data->log.info("onNext(): {}",data));
    }
}
