package scheduler;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.sql.Timestamp;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SchedulerEx {
    public static void main(String[] args) {
        Publisher<Integer> pub = sub -> {
            sub.onSubscribe(new Subscription() {
                @Override
                public void request(long n) {
                    sub.onNext(1);
                    sub.onNext(2);
                    sub.onNext(3);
                    sub.onNext(4);
                    sub.onNext(5);
                    sub.onComplete();
                }

                @Override
                public void cancel() {
                }
            });
        };

        // Thread 할당 , onNext 작업은 메인스레드가 아닌 싱글스레드에서 수행
        // todo : Thread ShutDown
        Publisher<Integer> subOnpub = sub ->{
          ExecutorService es = Executors.newSingleThreadExecutor(new CustomizableThreadFactory(){
              // thread naming
              @Override
              public String getThreadNamePrefix(){
                  return "subOnPub";
              }
          });

          // execute thread
          es.execute(()->pub.subscribe(sub));
        };

        Publisher<Integer> pubOnsub = sub -> {
            subOnpub.subscribe(new Subscriber<Integer>() {
                ExecutorService es = Executors.newSingleThreadExecutor(new CustomizableThreadFactory(){
                    @Override
                    public String getThreadNamePrefix(){
                        return "pubOnSub-";
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
                    log.info("Thread ShutDown at : "+new Timestamp(System.currentTimeMillis()));
                    es.shutdown();
                }
            });
        };

        pubOnsub.subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription s) {
                log.info("onSubscribe");
                s.request(Long.MAX_VALUE); // unlimited
            }

            @Override
            public void onNext(Integer integer) {
                log.info("onNext: " + integer);
            }

            @Override
            public void onError(Throwable t) {
                log.info("onError: " + t);
            }

            @Override
            public void onComplete() {
                log.info("onComplete");
            }
        });

        log.info("EXIT");
    }
}
