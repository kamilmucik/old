package pl.estrix.app.sheduler;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Worker {

//        @Scheduled(cron="0 * * * * *")
        public void runTask() {
                System.out.println("Runing at " + new Date());
    }
}
