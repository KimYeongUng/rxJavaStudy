package reactive;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// reactive stream
// Publisher -> [Data] -> Operator -> [Data2] -> Subsriber
public class PubSub2 {
    public static void main(String[] args) {

        Publisher<Integer> publisher = iterpub(Stream.iterate(1,a->a+1).limit(10).collect(Collectors.toList()));

        /**
         * map : data1 -> function(data1)(Operator) = data2
         */
        Publisher<String> mapPub = mapPub(publisher, s->"["+s+"]"); // operator 1
//        Publisher<Integer> mapPub2 = mapPub(mapPub,s->s/10); // operator 2
//        Publisher<Integer> sumPub = sumPub(publisher);
//        Publisher<Integer> reducePub = reducePub(publisher,0, (a,b)->a-b);
        mapPub.subscribe(logsub());
    }

//    private static Publisher<Integer> reducePub
//            (Publisher<Integer> publisher, int init, BiFunction<Integer, Integer, Integer> bifunc) {
//        return sub -> {
//
//            publisher.subscribe(new DelegateSub(sub) {
//                int result = init;
//                @Override
//                public void onNext(Integer integer) {
//                    result = bifunc.apply(result,integer);
//                }
//
//                @Override
//                public void onComplete(){
//                    sub.onNext(result);
//                    sub.onComplete();
//                }
//            });
//        };
//    }


    /**
     * Sum
     * onSubscribe
     * OnNext: 55
     * onComplete
     */
//    private static Publisher<T> sumPub(Publisher<T> publisher) {
//        return new Publisher<Integer>() {
//            @Override
//            public void subscribe(Subscriber<? super T> sub) {
//                publisher.subscribe(new DelegateSub(sub) {
//                    int sum = 0;
//                    @Override
//                    public void onNext(T t) {
//                        sum+=t;
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        sub.onNext(sum); // return sum 은 한번만 해야하기 때문에 complete시점에 next 호출
//                        sub.onComplete();
//                    }
//                });
//            }
//
//        };
//    }

    private static <T,R>Publisher<R> mapPub(Publisher<T> publisher, Function<T, R> func) {
        return sub -> publisher.subscribe(new DelegateSub<T,R>(sub){
            @Override
            public void onNext(T integer){
                sub.onNext(func.apply(integer));
            }
         });
    }

    private static  <T>Subscriber<T> logsub() {
        return new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("onSubscribe");
                s.request(Long.MAX_VALUE); // 무제한 발행

            }

            @Override
            public void onNext(T t) {
                System.out.println("OnNext: " + t);

            }

            // exception 오브젝트를 넘겨줌
            @Override
            public void onError(Throwable t) {
                System.out.println("Error: " + t);
            }

            // 완료여부
            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };
    }

    private static <T>Publisher<T> iterpub(Iterable<T> iter) {
        return sub -> sub.onSubscribe(new Subscription() {
            @Override
            public void request(long n) {
                try {
                    iter.forEach(sub::onNext);
                    sub.onComplete();
                } catch (RuntimeException t) {
                    sub.onError(t);
                }
            }

            @Override
            public void cancel() {

            }
        });
    }


}
