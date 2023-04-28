package performance.blocking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/v1")
public class V1MainController {
    private final RestTemplate template;
    URI baseuri = UriComponentsBuilder.newInstance().scheme("http")
            .host("localhost")
            .port("7070")
            .path("v1/data")
            .build()
            .encode()
            .toUri();

    public V1MainController(RestTemplateBuilder templateBuilder) {
        this.template = templateBuilder.build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{data-id}")
    public ResponseEntity<String> getData(@PathVariable("data-id")long dataId){
        URI getDataUri = UriComponentsBuilder.fromUri(baseuri)
                .path("{data-id}")
                .build()
                .expand(dataId)
                .encode()
                .toUri();

        ResponseEntity<String> response =
                template.getForEntity(getDataUri,String.class);
        String data = response.getBody();

        return ResponseEntity.ok(data);
    }
}
