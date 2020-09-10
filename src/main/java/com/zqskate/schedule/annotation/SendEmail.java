package com.zqskate.schedule.annotation;

import java.lang.annotation.*;

/**
 * @author soho.chan
 * @date 10/9/2020 17:10
 * @description TODO
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SendEmail {

    String to();

    String subject();

    String content();

}
