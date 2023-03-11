package rxjava2.coldhot;

import io.reactivex.rxjava3.core.Flowable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ColdPublisher {
    public static void main(String[] args) {
        Flowable<Integer> flowable = Flowable.just(1,2,3,4);

        log.info("SubScriber1 Timeline");
        flowable.subscribe(data->log.info("sub1: {}",data));
        log.info("Subscribe2 TImeline");
        flowable.subscribe(data->log.info("sub2: {}",data));
    }
}
