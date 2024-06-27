package me.qunqun.doctor.controller;

import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.doctor.entity.dto.PasswordDTO;
import me.qunqun.shared.entity.po.Doctor;
import me.qunqun.doctor.entity.dto.LoginDTO;
import me.qunqun.doctor.entity.vo.DoctorVO;
import me.qunqun.doctor.service.DoctorService;
import me.qunqun.doctor.entity.reps.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@Tag(name = "Login", description = "登录接口")
@RequestMapping("/api")
public class LoginController {

    @Autowired
    DoctorService doctorService;

    @Operation(summary = "登录")
    @RequestMapping("/signin")
    public Result<DoctorVO> login(@RequestBody LoginDTO loginDTO) {
        log.info("login: {}", loginDTO);
        Result<Doctor> rd = doctorService.login(loginDTO.getCode(), loginDTO.getPassword(), loginDTO.getType());
        if (rd.isError()) {
            return Result.error(rd.getCode(), rd.getMessage());
        }else {
            DoctorVO vo = DoctorVO.fromDoctor(rd.getData(), false);
            StpUtil.login(vo.getCode(), SaLoginConfig.setExtra("doctor", vo));
            var token = StpUtil.getTokenValue();
            vo.setToken(token);
            return Result.success(vo);
        }
    }

    @Operation(summary = "修改密码")
    @RequestMapping("/changePassword")
    public Result<Boolean> changePassword(@RequestBody PasswordDTO passwordDTO) {
        log.info("changePassword: {}", passwordDTO);
        Result<Boolean> rd = doctorService.changePassword(passwordDTO.getCode(), passwordDTO.getOldPassword(), passwordDTO.getNewPassword());
        if (rd.isError()) {
            return Result.error(rd.getCode(), rd.getMessage());
        }else {
            return Result.success(rd.getData());
        }
    }


}
