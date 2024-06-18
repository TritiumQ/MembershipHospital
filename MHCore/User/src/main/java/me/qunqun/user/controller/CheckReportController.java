package me.qunqun.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/check-report")
@Tag(name = "體檢報告管理", description = "體檢報告管理 API")
public class CheckReportController
{

}
