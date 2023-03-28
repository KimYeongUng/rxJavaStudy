package rxjava2.list;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SingleList {

    public static void main(String[] args) {
        Single<List<Integer>> list = Observable.just(1,2,3,4,5).toList();
        list.subscribe(listdata->log.info("list: {}",listdata));
    }
}
