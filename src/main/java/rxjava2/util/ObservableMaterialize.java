package rxjava2.util;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class ObservableMaterialize {
    public static void main(String[] args) throws InterruptedException {
        Observable.just( // error
                Observable.just(
                        getData().subscribeOn(Schedulers.io()),
                        getUser().subscribeOn(Schedulers.io())
                                .materialize()
                                .map(noti->{
                                    if(noti.isOnError())
                                        log.error("Error notification");
                                    return noti;
                                })
                                .filter(noti-> !noti.isOnError())
                                .dematerialize(noti->noti)
                ).subscribe(
                        data->log.info("data: {}",data),
                        err->log.info("err: {}",err.getMessage()),
                        ()->log.info("onComplete")
                )
        );
        Thread.sleep(2000L);
    }

    private static Observable<String> getData(){
        return Observable.fromIterable(Arrays.asList("a","b","c","d"));
    }

    private static Observable<String> getUser(){
        return Observable.fromIterable(Arrays.asList("user1","user2","user3","user4"))
                .map(
                        data->{
                            if(data.equals("user3"))
                                throw new RuntimeException();
                            return data;
                        }
                );
    }
}
