package webflux.functional;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration("dataRouter1")
public class DataRouter {

    @Bean
    public RouterFunction<?> routeData(@NotNull DataHandler handler){
        return route()
                .POST("/data",handler::createData)
                .PATCH("/data/{data-id}",handler::patchData)
                .GET("/data/{data-id}",handler::getData)
                .GET("/data/all",handler::getAllData)
                .build();
    }
}
