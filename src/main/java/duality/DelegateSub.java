package duality;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class DelegateSub<T,R> implements Subscriber<T> {
    Subscriber sub;
    public DelegateSub(Subscriber<? super R> sub) {
        this.sub = sub;
    }

    @Override
    public void onSubscribe(Subscription s) {
        sub.onSubscribe(s);
    }

    @Override
    public void onError(Throwable t) {
        sub.onError(t);
    }

    @Override
    public void onComplete() {
        sub.onComplete();
    }

    @Override
    public void onNext(T t) {
        sub.onNext(t);
    }
}
