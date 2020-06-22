Email 的 maven 依赖：
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```
Springboot 自带 Schedule ，无序额外导入：
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
配置 application.properties 配置文件

```properties
spring.mail.host=smtp.163.com #邮箱的smtp
spring.mail.username=xxxxx@163.com #邮箱号码
spring.mail.password=ZDD********VIC #授权码
spring.mail.default-encoding=UTF-8
# 超时时间
spring.mail.properties.mail.smtp.connectiontimeout=5000
spring.mail.properties.mail.smtp.timeout=3000
spring.mail.properties.mail.smtp.writetimeout=5000
```

创建 MailService 接口，包含四种邮件的发送方式：
```java
package com.zqskate.schedule.service;

/**
 * @author CHAN
 * @date 2020/06/22
 */
public interface MailService {

    /**
     * 发送简单邮件
     *
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleMail(String to, String subject, String content);

    /**
     * 发送带附件的邮件
     *
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件路径
     */
    void sendAttachmentsMail(String to, String subject, String content, String filePath);

    /**
     * 发送 html 邮件
     *
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendHtmlMail(String to, String subject, String content);

    /**
     * 发送图片邮件
     *
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param rscPath 地址
     * @param rscId id
     */
    void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);

}
```
实现类 MailServiceImpl：
```java
package com.zqskate.schedule.service.impl;

import com.zqskate.schedule.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author CHAN
 * @date 2020/06/22
 */
@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        try {
            javaMailSender.send(message);
            log.info(String.format("[success] [%s]", to));
        } catch (MailException ex) {
            log.info(String.format("[error:%s] [%s]", ex.getMessage(), to));
        }
    }

    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            assert fileName != null;
            // set email body
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.addAttachment(fileName, file);
            // send email
            javaMailSender.send(message);
            log.info(String.format("[success] [%s]", to));
        } catch (MessagingException ex) {
            log.info(String.format("[error:%s] [%s]", ex.getMessage(), to));
        }
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            javaMailSender.send(message);
            log.info(String.format("[success] [%s]", to));
        } catch (MessagingException ex) {
            log.info(String.format("[error:%s] [%s]", ex.getMessage(), to));
        }
    }

    @Override
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            File file = new File(rscPath);
            FileSystemResource res = new FileSystemResource(file);
            helper.addInline(rscId, res);
            javaMailSender.send(message);
            log.info(String.format("[success] [%s]", to));
        } catch (MessagingException ex) {
            log.info(String.format("[error:%s] [%s]", ex.getMessage(), to));
        }
    }
}
```
测试：
```java
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
```
定时任务：
```java
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
```

