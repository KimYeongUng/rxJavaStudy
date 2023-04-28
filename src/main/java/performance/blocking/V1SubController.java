package performance.blocking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/v1/data")
public class V1SubController {
    private Map<Long, String> map;

    @Autowired
    public V1SubController() {
        this.map = new HashMap<>();
        map.put(1L,"data1");
        map.put(2L,"data2");
        map.put(3L,"data3");
        map.put(4L,"data4");
        map.put(5L,"data5");
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{book-id}")
    public ResponseEntity<String> getData(@PathVariable("book-id")long dataId)
            throws InterruptedException {

        Thread.sleep(5000L); // processing time
        log.info("id: {} data: {}",dataId,this.map.get(dataId));
        String data = map.get(dataId);
        return ResponseEntity.ok(data);
    }
}
