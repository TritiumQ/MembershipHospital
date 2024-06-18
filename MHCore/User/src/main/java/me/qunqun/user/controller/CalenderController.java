package me.qunqun.user.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "日历接口", description = "日历管理API")
@RequestMapping("/api/calender")
public class CalenderController
{

}
