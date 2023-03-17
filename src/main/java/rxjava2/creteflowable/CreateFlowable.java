package rxjava2.creteflowable;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
public class CreateFlowable {
    public static void main(String[] args) throws InterruptedException {
        Flowable<String> flowable
                = Flowable.create(emitter -> {
                    String[] data = {"Hello","RxJava"};
                    for (String s: data){
                        if(emitter.isCancelled())
                            return;
                        emitter.onNext(s);
                    }
                    emitter.onComplete();
                }, BackpressureStrategy.BUFFER);

        flowable.observeOn(Schedulers.computation())
                .subscribe(new Subscriber<>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(String s) {
                        log.info("onNext(): {}", s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        log.error("onError(): {}", t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        log.info("onComplete()");
                    }
                });
        Thread.sleep(500L);
    }
}
