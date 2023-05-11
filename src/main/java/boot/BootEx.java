package boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@Slf4j
public class BootEx {

    @RestController
    static class MyController{
        @RequestMapping("/")
        public Mono<String> mapping(){
            log.info("Mono mapping ");
            Mono<String> m = Mono.fromSupplier(this::generateHello).doOnNext(log::info).log();
            String b = m.block(); // Mono to String (Data)
            log.info(b);
            log.info("hook");
            return m;
        }

        private String generateHello() {
            log.info("method Generate Hello");
            return "Hello WebFlux";
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(BootEx.class,args);
    }
}
