package timeattack;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import timeattack.consume.Quote;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class TimeAttack {

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }

    @Bean
    ApplicationRunner runner(RestTemplate restTemplate){
        return args -> {
            Quote q = restTemplate.getForObject("http://localhost:8080/api/random", Quote.class);
            log.info("quote: "+q);
        };
    }
    public static void main(String[] args) {

        SpringApplication.run(TimeAttack.class,args);
    }
}
