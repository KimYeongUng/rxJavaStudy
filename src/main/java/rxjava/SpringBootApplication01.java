package rxjava;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@SpringBootApplication
public class SpringBootApplication01 {

    @RestController
    public static class Controller{

        @RequestMapping(value="/hello",produces="application/json;charset=UTF-8")
        public Publisher<String> hello(String name){
            log.info("Parameter Name: "+name);
            return sub -> sub.onSubscribe(new Subscription() {
                @Override
                public void request(long n) {
                    sub.onNext("Hello World " + name);
                    sub.onComplete();
                }

                @Override
                public void cancel() {

                }
            });
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication01.class,args);
    }
}
