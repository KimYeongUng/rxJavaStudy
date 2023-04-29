package performance.nonblocking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("/v2/data")
@RestController
public class V2SubController {
    private Map<Long,String> data;


    @Autowired
    public V2SubController() {
        this.data = new HashMap<>();
        data.put(1L,"data1");
        data.put(2L,"data2");
        data.put(3L,"data3");
        data.put(4L,"data4");
        data.put(5L,"data5");

    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/{data-id}")
    public Mono<String> getData(@PathVariable("data-id")long dataId) throws InterruptedException {
        Thread.sleep(5000);

        String data = this.data.get(dataId);

        return Mono.just(data);
    }
}
