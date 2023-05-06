package operator;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FlatMapEx {
    public static void main(String[] args) {
        Flux.just("GOOD","BAD")
                .flatMap(feeling->
                   Flux.just("Morning","AfterNoon","Evening")
                           .map(time->feeling+" "+time)
                )
                .subscribe(log::info);
    }
}
