package imperative;

import reactor.core.publisher.Flux;


public class StreamEx {
    public static void main(String[] args) {
        Flux.range(0,100)
                .filter(n->n%2 != 0)
                .map(n->"num: "+n)
                .subscribe(System.out::println);
    }
}
