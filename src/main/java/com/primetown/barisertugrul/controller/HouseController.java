package com.primetown.barisertugrul.controller;

import com.primetown.barisertugrul.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("primetown")
public class HouseController {

    @Autowired
    private HouseRepository houseRepository;


}
