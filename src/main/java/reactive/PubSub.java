package reactive;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.*;

public class PubSub {
    public static void main(String[] args) throws InterruptedException {
        // publisher (Observable)
        // subscriber (Observer)
        Iterable<Integer> iter = Arrays.asList(1,2,3,4,5);
        ExecutorService ex = Executors.newSingleThreadExecutor();

        Flow.Publisher p = new Flow.Publisher() {
            @Override
            public void subscribe(Flow.Subscriber subscriber) {
                Iterator<Integer> iterator = iter.iterator();
                subscriber.onSubscribe(new Flow.Subscription() {
                    @Override
                    public void request(long n) {

                        ex.execute(()->{
                            int i = 0;
                            try {
                                while (i++ < n) {
                                    if (iterator.hasNext())
                                        subscriber.onNext(iterator.next());
                                    else {
                                        subscriber.onComplete();
                                        break;
                                    }
                                }
                            }catch(RuntimeException e){
                                subscriber.onError(e);
                            }
                        });

                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        };

        Flow.Subscriber s = new Flow.Subscriber() {
            Flow.Subscription subscription;

            // 구독 시작
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                // 몇개보낼건지
                this.subscription = subscription;
                this.subscription.request(1);
            }

            @Override
            public void onNext(Object item) {
                System.out.println("OnNext: "+item);
                this.subscription.request(1);
            }

            // exception 대응 - exception발생시 이걸 타고 넘어옴
            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError: "+throwable.getMessage());
                this.subscription.request(1);
            }
            // 끝
            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        p.subscribe(s);
        ex.awaitTermination(10,TimeUnit.HOURS);
        ex.shutdown();

    }
}
