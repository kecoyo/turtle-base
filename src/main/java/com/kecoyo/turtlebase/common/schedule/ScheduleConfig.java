package com.kecoyo.turtlebase.common.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableScheduling
public class ScheduleConfig {

    // // 每5秒执行一次
    // @Scheduled(fixedRate = 5000)
    // public void reportCurrentTime() {
    // System.out.println("当前时间: " + System.currentTimeMillis());
    // }

    // // 每天凌晨1点执行一次
    // @Scheduled(cron = "0 0 1 * * ?", zone = "Asia/Shanghai")
    // public void runEveryMidnight() {
    // System.out.println("每天凌晨1点执行任务");
    // }

    // // 每1分执行一次
    // @Scheduled(cron = "0/1 * * * * ?", zone = "Asia/Shanghai")
    // public void runEveryMinute() throws Exception {
    //     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //     String currentTime = dateFormat.format(new Date());
    //     log.info("当前时间: " + currentTime);
    // }
}
