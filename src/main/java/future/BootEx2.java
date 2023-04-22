package future;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
public class BootEx2 {

    @RestController
    static class MyContorller{
        RestTemplate restTemplate = new RestTemplate();
        @GetMapping("/rest")
        public String rest(int idx){
//            restTemplate.
            log.info("hello");
            return "hello "+idx;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(BootEx2.class,args);
    }
}
