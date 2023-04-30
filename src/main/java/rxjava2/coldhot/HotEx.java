package rxjava2.coldhot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

import static rxjava2.coldhot.ColdEx.getWorldTime;

@Slf4j
public class HotEx {
    public static void main(String[] args) throws InterruptedException {
        URI uri = UriComponentsBuilder.newInstance().scheme("http")
                .host("worldtimeapi.org")
                .port(80)
                .path("/api/timezone/Asia/Seoul")
                .build()
                .encode()
                .toUri();

        Mono<String> mono = getWorldTime(uri).cache();
        mono.subscribe(time->log.info("time: {}",time));
        Thread.sleep(2000);
        mono.subscribe(time->log.info("time: {}",time));
    }

}
