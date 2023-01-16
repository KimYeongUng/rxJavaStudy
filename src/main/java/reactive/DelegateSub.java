package reactive;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.function.Function;

public class DelegateSub<T,R> implements Subscriber<T>{
        private Subscriber<? super R> sub;

        public DelegateSub(Subscriber<? super R> sub) {
            this.sub = sub;
        }

        @Override
        public void onSubscribe(Subscription s) {
            sub.onSubscribe(s);
        }

        @Override
        public void onNext(T t) {
            // sub.onNext(t);
        }

        @Override
        public void onError(Throwable t) {
            sub.onError(t);
        }

        @Override
        public void onComplete() {
            sub.onComplete();
        }

}
