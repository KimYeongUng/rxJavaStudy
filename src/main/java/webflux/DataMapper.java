package webflux;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,implementationName = "DataMapper")
public interface DataMapper {
    Data dataPostToData(DataDto.Post requestBody);
    Data dataToDataResponse(Data dataMono);

    Data dataPatchToData(DataDto.Patch requestBody);

    List<DataDto.Response> alldataToDataResponse(List<Data> data);
}
