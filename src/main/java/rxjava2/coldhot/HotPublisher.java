package rxjava2.coldhot;

import io.reactivex.rxjava3.processors.PublishProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HotPublisher {
    public static void main(String[] args) {
        PublishProcessor<Integer> processor = PublishProcessor.create();
        log.info("Subscriber 1");
        processor.subscribe(data->log.info("sub1: {}",data));
        processor.onNext(1);
        processor.onNext(2);
        log.info("Subscriber 2");
        processor.subscribe(data->log.info("sub2 {}",data));
        processor.onNext(3);
        processor.onNext(4);
    }
}
