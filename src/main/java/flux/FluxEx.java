package flux;

import reactor.core.publisher.Flux;

public class FluxEx {
    public static void main(String[] args) {
        Flux.just("Hello","World")
                .map(String::toUpperCase)
                .subscribe(System.out::println);
    }
}
