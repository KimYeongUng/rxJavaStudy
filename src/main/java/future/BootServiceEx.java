package future;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class BootServiceEx {

    @RestController
    static class MyService{
        @GetMapping("/service")
        public String res(String msg) throws InterruptedException {
            Thread.sleep(2000);
            return "service "+msg;
        }

        @GetMapping("/service2")
        public String res2(String msg) throws InterruptedException {
            Thread.sleep(1000);
            return "service "+msg;
        }
    }

    public static void main(String[] args) {
        System.setProperty("server.port","8081");
        System.setProperty("server.tomcat.max-threads","1000");
        SpringApplication.run(BootServiceEx.class,args);
    }
}
