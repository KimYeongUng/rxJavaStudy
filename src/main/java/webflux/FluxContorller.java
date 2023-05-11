package webflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;


@Slf4j
@RequestMapping(path = "/flux/data",produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class FluxContorller {
    Map<Long,Data> dataMap;

    @Autowired
    public FluxContorller(Map<Long,Data> dataMap){
        this.dataMap = dataMap;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{data-id}")
    public Mono<Data> getData(@PathVariable("data-id") long dataid) throws InterruptedException {
        Thread.sleep(2000L);
        Data data = dataMap.get(dataid);
        log.info("# response: {} , {}",data.getDataId(),data.getValue());
        return Mono.just(data);
    }
}
