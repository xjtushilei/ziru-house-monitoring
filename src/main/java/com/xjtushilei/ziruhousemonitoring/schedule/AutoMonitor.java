package com.xjtushilei.ziruhousemonitoring.schedule;

import com.aliyuncs.exceptions.ClientException;
import com.xjtushilei.ziruhousemonitoring.entity.Log;
import com.xjtushilei.ziruhousemonitoring.repository.LogRepository;
import com.xjtushilei.ziruhousemonitoring.service.AliyunDmService;
import com.xjtushilei.ziruhousemonitoring.service.MonitorHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @author scriptshi
 * 2018/6/6
 */
@Component
@EnableScheduling
public class AutoMonitor {

    @Value("${my.userName}")
    private String userName;

    @Value("${my.URL}")
    private String URL;

    @Value("${my.oldImgURL}")
    private String oldImgURL;

    @Autowired
    MonitorHouseService monitorHouseService;

    @Autowired
    LogRepository logRepository;

    @Autowired
    AliyunDmService aliyunDmService;


    @Scheduled(fixedRate = 1000 * 30)
    public void auto() {
        if (logRepository.countByLog("true") == 0) {
            try {
                if (monitorHouseService.findStatus(URL, oldImgURL)) {

                    aliyunDmService.send("房源变更通知");
                    logRepository.save(new Log("true", "监测成功"));
                    System.out.println("监测ing....找到了");
                } else {
                    logRepository.save(new Log("false", "监测成功"));
                    System.out.println("监测ing....没找到");
                }
            } catch (Exception e) {
                logRepository.save(new Log(e.getMessage(), "监测失败"));
                e.printStackTrace();
            }

        } else {
            System.out.println("监测可以停止了.....已经找到房子");
        }

    }


    @Scheduled(cron = "0 0 23 ? * *")
    public void autoDay() {
        try {
            long success = logRepository.countByType("监测成功");
            long error = logRepository.countByType("监测失败");
            String status = logRepository.countByLog("true") == 0 ? "无所收获" : "房源发生变化";
            aliyunDmService.sendEmail("房源监测日报", "<h3 align='center'>日报</h3>\n" +
                    "<br>\n" +
                    "尊敬的 <span style=\"color: deeppink\">" + userName + "</span> :\n" +
                    "<br>\n" +
                    "您关注的自如房子 <a href='" + URL + "' style=\"color: blue\">" + URL + "</a> 今日监控数据如下：\n" +
                    "<br>\n" +
                    "<b style=\"color: green\">监测成功次数</b>：" + success +
                    "<br>\n" +
                    "<b style=\"color: red\">监测失败次数</b>：" + error +
                    "<br>\n" +
                    "<b style=\"color: blue\">目前状态</b>：" + status +
                    "<br>\n" +
                    "祝您生活愉快~身体健康！\n" +
                    "<br>\n" +
                    "你的小可爱");
        } catch (ClientException e) {
            e.printStackTrace();
            logRepository.save(new Log(e.getMessage(), "发送日报失败"));
        }


    }

}