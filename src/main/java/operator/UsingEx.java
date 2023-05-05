package operator;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Slf4j
public class UsingEx {
    public static void main(String[] args) {
        Path path = Paths.get("resources/sample/sample.txt");

        Flux
                .using(()-> Files.lines(path),Flux::fromStream, Stream::close)
                .subscribe(log::info);
    }
}
