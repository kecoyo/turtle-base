package com.kecoyo.turtle.common.schedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ScheduleConfig {

    // // 每5秒执行一次
    // @Scheduled(fixedRate = 5000)
    // public void reportCurrentTime() {
    //     System.out.println("当前时间: " + System.currentTimeMillis());
    // }

    // // 每天凌晨1点执行一次
    // @Scheduled(cron = "0 0 1 * * ?")
    // public void runEveryMidnight() {
    //     System.out.println("每天凌晨1点执行任务");
    // }
}
