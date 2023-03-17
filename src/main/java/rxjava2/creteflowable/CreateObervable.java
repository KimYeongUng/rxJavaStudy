package rxjava2.creteflowable;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateObervable {
    public static void main(String[] args) throws InterruptedException {
        Observable<String> observable
                = Observable.create(emitter -> {
                    String[] datas = {"Hello","RxJava"};
                    for(String s: datas){
                        if(emitter.isDisposed())
                            return;
                        emitter.onNext(s);
                    }
                    emitter.onComplete();
                });

        observable.observeOn(Schedulers.computation())
                .subscribe(new Observer<>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // do nothing
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        log.info("OnNext(): {}",s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        log.error("onError(): {}",e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        log.info("onComplete() ");
                    }
                });

        Thread.sleep(500L);
    }
}
