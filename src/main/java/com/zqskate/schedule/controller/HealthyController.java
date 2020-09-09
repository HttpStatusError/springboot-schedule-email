package com.zqskate.schedule.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author soho.chan
 * @date 9/9/2020 15:58
 * @description TODO
 */
@RestController
public class HealthyController {

    @Value("${app.project.name}")
    private String projectName;

    @Value("${app.build.time}")
    private String buildTime;

    @Value("${spring.profiles.active}")
    private String profile;

    @GetMapping("version")
    public ResponseEntity<Object> healthyCheck() {
        String format = String.format("%s_%s_%s", profile, projectName, buildTime);
        return new ResponseEntity<Object>(format, HttpStatus.OK);
    }

}
