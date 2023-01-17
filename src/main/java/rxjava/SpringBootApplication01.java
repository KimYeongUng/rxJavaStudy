package rxjava;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
public class SpringBootApplication01 {

    @RestController
    public static class Controller{
        @RequestMapping("/hello")
        public Publisher<String> hello(String name){

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
