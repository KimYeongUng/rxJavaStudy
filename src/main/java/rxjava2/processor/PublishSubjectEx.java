package rxjava2.processor;

import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PublishSubjectEx {
    public static void main(String[] args) {
        PublishSubject<Integer> subject = PublishSubject.create();

        subject.subscribe(data->log.info("subject1: {}",data));
        subject.onNext(3000);
        subject.subscribe(data->log.info("subject2: {}",data));
        subject.onNext(4000);
        subject.subscribe(data->log.info("subject3: {}",data));
        subject.onNext(5000);

        subject.subscribe(
                data->log.info("subject4: {}",data),
                err->log.error(err.getMessage()),
                ()->log.info("onComplete()")
        );
        subject.onComplete();
    }
}
