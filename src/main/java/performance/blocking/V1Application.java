package performance.blocking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalTime;

@SpringBootApplication
@Slf4j
public class V1Application {
    private URI baseuri = UriComponentsBuilder.newInstance().scheme("http")
            .host("localhost")
            .port(8080)
            .path("/v1/data")
            .build()
            .encode()
            .toUri();


    public static void main(String[] args) {
        SpringApplication.run(V1Application.class,args);
    }

    @Bean
    public RestTemplateBuilder restTemplate(){
        return new RestTemplateBuilder();
    }

    @Bean
    public CommandLineRunner run(){
        return (String ... args)->{
          log.info("# request start : {}", LocalTime.now());
          for (int i=1;i<=5;i++){
              String data = getData(i);
              log.info("{}: Data: {}",LocalTime.now(),data);
          }

        };
    }

    private String getData(long dataId) {
        RestTemplate template = new RestTemplate();

        URI getDataUri = UriComponentsBuilder.fromUri(baseuri)
                .path("/{data-id}")
                .build()
                .expand(dataId)
                .encode()
                .toUri();

        ResponseEntity<String> res =
                template.getForEntity(getDataUri,String.class);

        String data = res.getBody();

        return data;
    }


}
