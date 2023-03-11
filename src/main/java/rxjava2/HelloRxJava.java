package rxjava2;

import io.reactivex.rxjava3.core.Observable;

public class HelloRxJava {
    public static void main(String[] args) {
        Observable<String> observable = Observable.just("Hello","World");
        observable.subscribe(System.out::println);
        // or
        observable.subscribe(data->System.out.println("data: "+data));
    }
}
