package rxjava;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
public class SpringBootApplication01 {

    @RestController
    public static class Controller{
        private Logger log = LoggerFactory.getLogger(SpringBootApplication.class);

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
