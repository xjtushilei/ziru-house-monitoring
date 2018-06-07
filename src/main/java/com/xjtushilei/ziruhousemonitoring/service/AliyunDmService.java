package com.xjtushilei.ziruhousemonitoring.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;

/**
 * @author scriptshi
 * 2018/6/6
 */
@Service
public class AliyunDmService {
    public static void main(String[] args) {
    }

    @Value("${my.accessKeyId}")
    private String accessKeyId;

    @Value("${my.secret}")
    private String secret;

    @Value("${my.setToAddress}")
    private String setToAddress;

    @Value("${my.setAccountName}")
    private String setAccountName;

    @Value("${my.userName}")
    private String userName;

    @Value("${my.URL}")
    private String URL;

    public void send(String subject) throws IOException, ClientException {
        //读取模版
        String htmlBody = FileUtils.readFileToString(ResourceUtils.getFile("classpath:tpl/" + subject + ".html"), "utf-8");
        //替换模版
        htmlBody = htmlBody.replace("{{姓名}}", userName).replace("{{网址}}", URL);
        sendEmail(subject, htmlBody);

    }

    public void sendEmail(String subject, String body) throws ClientException {
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, secret);

        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        request.setAccountName(setAccountName);
        request.setFromAlias("小可爱");
        request.setAddressType(1);
        request.setClickTrace("1");
        request.setTagName("notification");
        request.setReplyToAddress(true);
        request.setToAddress(setToAddress);
        request.setSubject(subject);
        request.setHtmlBody(body);
        client.getAcsResponse(request);

    }
}
