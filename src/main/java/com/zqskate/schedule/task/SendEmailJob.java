package com.zqskate.schedule.task;

import com.zqskate.schedule.annotation.SendEmail;
import com.zqskate.schedule.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author CHAN
 * @date 2020/06/22
 */
@Component
@EnableScheduling
public class SendEmailJob {

    private static final Logger log = LoggerFactory.getLogger(SendEmailJob.class);

    @Resource
    private MailService mailService;

    /**
     * 加入 Scheduled 使用 cron 表达式实现定时发送邮件
     *
     *  .----------------- Seconds (0 - 59)
     *  | .-------------- Minutes (0 - 59)
     *  | | .----------- Hours (0 - 23)
     *  | | | .-------- Day of Month (0 - 31)
     *  | | | | .----- Month (1 - 12)
     *  | | | | | .-- Day of Week (1 - 7 or SUN - SAT)
     *  | | | | | |
     *  * * * * * * cron expression
     */
    @Scheduled(cron = "0/1 * * * * *")
    public void sendEmail() {
        String to = "xxxxxx@qq.com";
        String subject = "hello, This is a test email";
        String content = "If you see this email, the test is successful";
        mailService.sendSimpleMail(to, subject, content);
        log.info(String.format("[%s]:[%s]:[%s]", to, subject, content));
    }

    @Scheduled(cron = "0 0 0 0 1 *")
    @SendEmail(to = "xxxxxx@qq.com",
            subject = "hello, This is a test email",
            content = "If you see this email, the test is successful")
    public void sendEmailAop() {}

    @Scheduled(cron = "0 0 0 0 1 *")
    @SendEmail(to = "", subject = "", content = "")
    public void sendEmail2Aop() {}

    @Scheduled(cron = "0 0 0 0 1 *")
    @SendEmail(to = "", subject = "", content = "")
    public void sendEmail3Aop() {}

}
