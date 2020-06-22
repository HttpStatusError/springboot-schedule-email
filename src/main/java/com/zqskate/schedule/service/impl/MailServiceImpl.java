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
