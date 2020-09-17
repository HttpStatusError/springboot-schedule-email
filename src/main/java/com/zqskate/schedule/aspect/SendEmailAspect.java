package com.zqskate.schedule.aspect;

import cn.hutool.extra.mail.MailUtil;
import com.zqskate.schedule.annotation.SendEmail;
import com.zqskate.schedule.service.MailService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author soho.chan
 * @date 10/9/2020 17:09
 * @description TODO
 */
@Component
@Aspect
public class SendEmailAspect {

    @Pointcut("@annotation(com.zqskate.schedule.annotation.SendEmail)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public void around(ProceedingJoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        SendEmail anno = method.getAnnotation(SendEmail.class);
        String to = anno.to();
        String subject = anno.subject();
        String content = anno.content();
        MailUtil.sendText(to, subject, content);
    }

}
