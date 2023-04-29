package performance.nonblocking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalTime;

@SpringBootApplication
@Slf4j
public class V2Application {
    private final URI baseUri = UriComponentsBuilder.newInstance().scheme("http")
            .host("localhost")
            .port(6060)
            .build()
            .encode()
            .toUri();

    public static void main(String[] args) {
        System.setProperty("reactor.netty.ioWorkerCount","1");
        SpringApplication.run(V2Application.class,args);
    }

    @Bean
    public CommandLineRunner run(){
        return (String ... args)->{
            log.info("#request time: {}", LocalTime.now());

            for (int i=1;i<=5;i++){

                this.getData(i)
                        .subscribe(data-> log.info("{} data: {}",LocalTime.now(),data));
            }
        };
    }

    private Mono<String> getData(long dataId){
        URI getDataUri = UriComponentsBuilder.fromUri(baseUri)
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
