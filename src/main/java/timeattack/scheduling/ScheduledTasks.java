package timeattack.scheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class ScheduledTasks {
    public static final SimpleDateFormat simpleDateFormat= new  SimpleDateFormat("HH:mm:ss");

    // every 5 second
    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime(){
        log.info(simpleDateFormat.format(new Date()));
    }
}
