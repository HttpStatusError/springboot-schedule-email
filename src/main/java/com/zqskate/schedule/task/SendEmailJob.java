package com.zqskate.schedule.task;

import com.zqskate.schedule.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author CHAN
 * @date 2020/06/22
 */
@Slf4j
@Component
@EnableScheduling
public class SendEmailJob {

    @Resource
    private MailService mailService;

    /**
     * cron 表达式
     */
    @Scheduled(cron = "0/1 * * * * *")
    public void sendEmail() {
        String to = "xxxxxx@qq.com";
        String subject = "hello, This is a test email";
        String content = "If you see this email, the test is successful";
        mailService.sendSimpleMail(to, subject, content);
        log.info(String.format("[%s]:[%s]:[%s]", to, subject, content));
    }

}
