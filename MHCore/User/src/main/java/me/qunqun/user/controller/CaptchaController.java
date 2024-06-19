package me.qunqun.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "验证码管理", description = "验证码管理API")
@RequestMapping("/api/captcha")
@Slf4j
public class CaptchaController
{

}
