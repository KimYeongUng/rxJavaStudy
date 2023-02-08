package rxjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class RemoteService {

    @RestController
    public static class MyController{

        @GetMapping("/service1")
        public String rest(String req) throws InterruptedException {
            Thread.sleep(2000);
            // throw new RuntimeException(); // generate ERROR
            return req+"service1/";
        }

        @GetMapping("/service2")
        public String rest2(String req) throws InterruptedException {
            Thread.sleep(2000);
            return req+"service2/";
        }
    }

    public static void main(String[] args) {
        System.setProperty("SERVER.PORT","8081");
        System.setProperty("server.tomcat.max.threads","1000");
        SpringApplication.run(SpringBootApplication03.class,args);
    }

}
