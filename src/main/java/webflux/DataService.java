package webflux;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DataService {
    private final DataMapper mapper;

    DataService(DataMapper mapper){
        this.mapper = mapper;
    }
    public Mono<Data> createData(Mono<DataDto.Post> data){
        return data.flatMap(d->Mono.just(mapper.dataPostToData(d)));
    }

    public Mono<Data> updateData(final Long dataId,Mono<DataDto.Patch> data) {
        return data.flatMap(patch -> {
            patch.setDataId(dataId);
            return Mono.just(mapper.dataPatchToData(patch));
        });
    }

    public Mono<Data> findData(Long dataId) {
        return Mono.just(
                new Data(dataId,"value1","value2")
        );
    }
}
