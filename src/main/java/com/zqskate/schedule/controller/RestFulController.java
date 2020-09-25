package com.zqskate.schedule.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author soho.chan
 * @date 25/9/2020 15:19
 * @description TODO
 */
@RestController
public class RestFulController {

    @GetMapping
    public List<String> getArrayList() {
        List<String> list = new ArrayList<>();
        list.add("list");
        return list;
    }

}
