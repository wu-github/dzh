package com.wurd.bd.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TestSchedule {

    @Scheduled(cron = "0 0/5 * * * ?")
    public void testCron() {
        Date now = new Date();
        System.out.println("testCron: " + now);
    }

    @Scheduled(fixedRate = 5000)
    public void testFixedRate() {
        Date now = new Date();
        System.out.println("testFixedRate: " + now);
    }

}
