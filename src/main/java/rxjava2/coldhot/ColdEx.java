package rxjava2.coldhot;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
public class ColdEx {
    public static void main(String[] args) throws InterruptedException {
        URI uri = UriComponentsBuilder.newInstance().scheme("http")
                .host("worldtimeapi.org")
                .port(80)
                .path("/api/timezone/Asia/Seoul")
                .build()
                .encode()
                .toUri();
        Mono<String> mono = getWorldTime(uri);
        mono.subscribe(time->log.info("time: {}",time));
        Thread.sleep(2000);
        mono.subscribe(time->log.info("time: {}",time));

    }

    static Mono<String> getWorldTime(URI uri) {
        return WebClient.create()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .map(res->{
                   DocumentContext jsonContext = JsonPath.parse(res);
                   String dateTime = jsonContext.read("$.dateTime");
                   return dateTime;
                });
    }
}
