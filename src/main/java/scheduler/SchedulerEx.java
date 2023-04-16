package scheduler;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SchedulerEx {
    public static void main(String[] args) {
        Publisher<Integer> publisher = subscriber-> subscriber.onSubscribe(new Subscription() {
            @Override
            public void request(long n) {
                log.info("request");
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                subscriber.onNext(4);
                subscriber.onNext(5);
                subscriber.onComplete();
            }

            @Override
            public void cancel() {

            }
        });

        // operator
        Publisher<Integer> subOnPub = sub ->{
            ExecutorService es = Executors.newSingleThreadExecutor(new CustomizableThreadFactory(){
                @Override
                public String getThreadNamePrefix(){
                    return "subOn-";
                }
            });
            es.execute(()->publisher.subscribe(sub));
            es.shutdown();
        };

        // operator2
        Publisher<Integer> pubOnpub = sub->{
            subOnPub.subscribe(new Subscriber<Integer>() {
                ExecutorService es = Executors.newSingleThreadExecutor(new CustomizableThreadFactory(){
                    @Override
                    public String getThreadNamePrefix(){
                        return "pubOn-";
                    }
                });

                @Override
                public void onSubscribe(Subscription s) {
                    sub.onSubscribe(s);
                }

                @Override
                public void onNext(Integer integer) {
                    es.execute(()->sub.onNext(integer));
                }

                @Override
                public void onError(Throwable t) {
                    es.execute(()->sub.onError(t));
                    es.shutdown();
                }

                @Override
                public void onComplete() {
                    es.execute(sub::onComplete);
                    es.shutdown();
                }
            });
        };

        pubOnpub.subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription s) {
                log.info("onSubscribe");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                log.debug("onNext: {}",integer);
            }

            @Override
            public void onError(Throwable t) {
                log.error("onError : {}",t.getMessage());
            }

            @Override
            public void onComplete() {
                log.debug("onComplete");
            }
        });


        log.info("EXIT");
    }
}
