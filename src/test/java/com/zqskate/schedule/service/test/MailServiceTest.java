package com.zqskate.schedule.service.test;

import com.zqskate.schedule.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author CHAN
 * @date 2020/06/22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {
    String to = "xxxxxx@qq.com";
    String subject = "hello, This is a test email";
    String content = "If you see this email, the test is successful";

    @Resource
    private MailService mailService;

    @Test
    public void testSendSimpleMail() {
        mailService.sendSimpleMail(to, subject, content);
    }

    @Test
    public void testSendAttachmentsMail() {
        String filePath = "F:\\file.txt";
        mailService.sendAttachmentsMail(to, subject, content, filePath);
    }

    @Test
    public void testSendInlineResourceMail() {
        String filePath = "F:\\img.jpg";
        String rscId = "img001";
        String content = "<html><body><img width='250px' src='cid:" + rscId + "'></body></html>";
        mailService.sendInlineResourceMail(to, subject, content, filePath, rscId);
    }

    @Test
    public void testSendHtmlMail() {
        String content = "<html><body><h1>Hello World!</h1></body></html>";
        mailService.sendHtmlMail(to, subject, content);
    }
}
