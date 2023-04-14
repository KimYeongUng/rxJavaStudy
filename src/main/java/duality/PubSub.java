package duality;


import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class PubSub {
    public static void main(String[] args) {
        Iterable<Integer> iter = Stream.iterate(1, n -> n + 1).limit(10).collect(Collectors.toList());

        Publisher<Integer> pub = getSubscribe(iter);
        //Publisher<String> mapPub = mapPub(pub,a->"["+a+"]");
        Publisher<StringBuilder> reducePub = reducePub(pub,new StringBuilder(), StringBuilder::append);
        Subscriber<StringBuilder> subscriber = getSubscriber();

        reducePub.subscribe(subscriber);
    }

    private static <T,R>Publisher<R> reducePub(Publisher<T> pub, R init, BiFunction<R, T, R> bf) {
        return s -> pub.subscribe(new DelegateSub<T,R>(s){
            R res = init;

            @Override
            public void onNext(T i){
                res = (bf.apply(res,i));
            }

            @Override
            public void onComplete(){
                s.onNext(res);
                s.onComplete();
            }
        });
    }
//
//
//    private static Publisher<Integer> sumPub(Publisher<Integer> pub) {
//        return s -> pub.subscribe(new DelegateSub(s){
//            int sum = 0;
//            @Override
//            public void onNext(Integer i){
//                sum+=i;
//            }
//
//            @Override
//            public void onComplete(){
//                s.onNext(sum);
//                s.onComplete();
//            }
//        });
//    }

    private static <T,R> Publisher<R> mapPub(Publisher<T> pub,Function<T,R> func){
        return sub -> pub.subscribe(new DelegateSub<T,R>(sub){
            @Override
            public void onNext(T t){
                sub.onNext(func.apply(t));
            }
        });
    }

    private static <T> Subscriber<T> getSubscriber() {
        return new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription s) {
                log.info("onSubscribe");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(T t) {
                log.info("onNext: "+t);
            }

            @Override
            public void onError(Throwable t) {
                log.error("ERR");
            }

            @Override
            public void onComplete() {
                log.error("Complete");
            }
        };
    }

    private static Publisher<Integer> getSubscribe(Iterable<Integer> iter) {
        return s -> {
            log.info("subscribe");

            s.onSubscribe(new Subscription() {
                @Override
                public void request(long n) {
                    iter.forEach(s::onNext);
                    s.onComplete();
                }

                @Override
                public void cancel() {

                }
            });
        };
    }

}
