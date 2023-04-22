package scheduling;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import timeattack.scheduling.ScheduledTasks;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

public class SchedulerTest {
    @SpyBean
    ScheduledTasks scheduledTask;

    @Test
    void reportCurrentTime(){
        await().atMost(10, TimeUnit.SECONDS).untilAsserted(()->{
            verify(scheduledTask,atLeast(2)).reportCurrentTime();
        });
    }
}
