package com.xjtushilei.ziruhousemonitoring.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author scriptshi
 * 2018/6/6
 */
@Service
public class MonitorHouseService {

    public static void main(String[] args) throws IOException {
        String oldImgUrl = "http://pic.ziroom.com/static/images/slist_1207/defaultPZZ/mumian-loading.jpg";
        new MonitorHouseService().findStatus("http://hz.ziroom.com/z/vr/61226284.html", oldImgUrl);
        new MonitorHouseService().findStatus("http://hz.ziroom.com/z/vr/61289406.html", oldImgUrl);
    }

    @Autowired
    AliyunDmService aliyunDmService;

    public boolean findStatus(String htmlUrl, String oldImgUrl) throws IOException {
        Document document = Jsoup
                .connect(htmlUrl)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                .get();
        String img = document.select("#cao > ul > li:nth-child(1) > a > img").first().attr("src");
        return !oldImgUrl.equals(img);
    }

}
