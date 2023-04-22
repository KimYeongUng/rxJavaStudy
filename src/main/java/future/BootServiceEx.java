package future;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class BootServiceEx {

    @RestController
    static class MyService{
        @GetMapping("/service")
        public String res(String msg){
            return "service"+msg;
        }
    }

    public static void main(String[] args) {
        System.setProperty("SERVER_PORT","8081");
        System.setProperty("server.tomcat.max-threads","1000");
        SpringApplication.run(BootServiceEx.class,args);
    }
}
