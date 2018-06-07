package com.xjtushilei.ziruhousemonitoring.init;

import com.xjtushilei.ziruhousemonitoring.entity.Log;
import com.xjtushilei.ziruhousemonitoring.repository.LogRepository;
import com.xjtushilei.ziruhousemonitoring.service.AliyunDmService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author scriptshi
 * 2018/6/6
 */
@Component
public class StartMail {
    @Bean
    public ApplicationRunner runner(AliyunDmService aliyunDmService, LogRepository logRepository) {
        return (args) -> {
            if (logRepository.countByLog("加入房源监测计划") == 0) {
                aliyunDmService.send("加入房源监测计划");
                logRepository.save(new Log("加入房源监测计划", "监测初始化"));
                System.out.println("加入房源监测计划");
            }
            else {
                System.out.println("已经加入房源监测计划，无需重复加入");
                logRepository.save(new Log("已经加入房源监测计划，无需重复加入", "监测初始化"));
            }
        };
    }
}
