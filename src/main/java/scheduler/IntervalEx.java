package scheduler;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class IntervalEx {
    public static void main(String[] args) {

        Publisher<Integer> pub = sub -> sub.onSubscribe(new Subscription() {
            int no = 0;
            volatile boolean cancel = false;

            @Override
            public void request(long n) {
                ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
                exec.scheduleAtFixedRate(()->{
                    if(cancel){
                        exec.shutdown();
                        return;
                    }
                    sub.onNext(no++);
                },0,300, TimeUnit.MICROSECONDS);


            }

            @Override
            public void cancel() {
                cancel = true;
            }
        });

        Publisher<Integer> takePub = sub ->{
            pub.subscribe(new Subscriber<>() {
                int count = 0;
                Subscription subs;
                @Override
                public void onSubscribe(Subscription s) {
                    sub.onSubscribe(s);
                }

                @Override
                public void onNext(Integer integer) {
                    sub.onNext(integer);
                    if(++count > 10){
                        subs.cancel();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    sub.onError(t);
                }

                @Override
                public void onComplete() {
                    sub.onComplete();
                }
            });
        };

        takePub.subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription sub) {
                log.info("onSubscribe");
                sub.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                log.info("onNext: "+integer);

            }

            @Override
            public void onError(Throwable t) {
                log.info("onError");

            }

            @Override
            public void onComplete() {
                log.info("onComplete");

            }
        });
    }
}
