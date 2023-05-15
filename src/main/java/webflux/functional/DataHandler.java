package webflux.functional;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import webflux.Data;
import webflux.DataDto;
import webflux.DataMapper;

import java.net.URI;
import java.util.List;
@Component("dataHandler")
public class DataHandler {
    private final DataMapper mapper;

    public DataHandler(DataMapper mapper){
        this.mapper = mapper;
    }

    public Mono<ServerResponse> createData(ServerRequest request){
        return request.bodyToMono(DataDto.Post.class)
                .map(mapper::dataPostToData)
                .flatMap(data ->
                            ServerResponse.created(URI.create("/data/"+data.getDataId()))
                                    .build()
                        );
    }

    public Mono<ServerResponse> getData(ServerRequest request){
        long dataId = Long.parseLong(request.pathVariable("data-id"));
        Data data =
                new Data(dataId,"value1","value2");

        return ServerResponse.ok().bodyValue(mapper.dataToDataResponse(data))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> patchData(ServerRequest request){
        final long dataId = Long.parseLong(request.pathVariable("data-id"));

        return request
                .bodyToMono(DataDto.Patch.class)
                .map(patch -> {
                    patch.setDataId(dataId);
                    return mapper.dataPatchToData(patch);
                })
                .flatMap(data -> ServerResponse.ok().bodyValue(mapper.dataToDataResponse(data)));
    }

    public Mono<ServerResponse> getAllData(ServerRequest request){
        List<Data> data =
                List.of(new Data(1L,"value1","value2"),
                        new Data(2L,"value3","value4"),
                        new Data(3L,"value5","value6")
                );

        return ServerResponse.ok().bodyValue(mapper.alldataToDataResponse(data));
    }
}
