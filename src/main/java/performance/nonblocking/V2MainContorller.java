package performance.nonblocking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@RequestMapping("v2/data")
@RestController
public class V2MainContorller {
    URI baseuri = UriComponentsBuilder.newInstance().scheme("http")
            .host("localhost")
            .port(5050)
            .build()
            .encode()
            .toUri();


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{data-id}")
    public Mono<String> getData(@PathVariable("data-id")long dataId){
        URI getDataUri = UriComponentsBuilder.fromUri(baseuri)
                .path("/{data-id}")
                .build()
                .expand(dataId)
                .encode()
                .toUri();

        return WebClient.create()
                .get()
                .uri(getDataUri)
                .retrieve()
                .bodyToMono(String.class);

    }
}
