package webflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
@RequestMapping(path = "/flux/data",produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class FluxContorller {

    private final DataService service;
    private final DataMapper mapper;

    //webclient
    @GetMapping("/webclient")
    public Mono<String> doWebClient(){
        WebClient client = WebClient.create();
        return client
                .get()
                .uri("http://localhost:8081/webclient/create")
                .retrieve()
                .bodyToMono(String.class);
    }
    public FluxContorller(DataService service,DataMapper mapper){
        this.mapper = mapper;
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Data> postData(@RequestBody Mono<DataDto.Post> requestBody){
        Mono<Data> res = service.createData(requestBody);
        return res.flatMap(data -> Mono.just(mapper.dataToDataResponse(data)));
    }

    @PatchMapping("/{data-id}")
    public Mono<Data> patchData(@PathVariable("data-id") Long dataId,@RequestBody Mono<DataDto.Patch> requestBody){
        Mono<Data> res = service.updateData(dataId,requestBody);
        return res.flatMap(data -> Mono.just(mapper.dataToDataResponse(data)));
    }

    @GetMapping("/{data-id}")
    public Mono<Data> getData(@PathVariable("data-id") Long dataId){
        return service.findData(dataId).flatMap(data->Mono.just(mapper.dataToDataResponse(data)));
    }

}
