package me.qunqun.doctor.controller;

import me.qunqun.shared.entity.po.Doctor;
import me.qunqun.doctor.entity.dto.LoginDTO;
import me.qunqun.doctor.entity.vo.DoctorVO;
import me.qunqun.doctor.service.DoctorService;
import me.qunqun.doctor.utils.JwtUtil;
import me.qunqun.doctor.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/start")
public class LoginController {

    @Autowired
    DoctorService doctorService;

    @RequestMapping("/login")
    public Result<DoctorVO> login(LoginDTO loginDTO) {
        Result<Doctor> rd = doctorService.login(loginDTO.getCode(), loginDTO.getPassword());
        if (rd.isError()) {
            return Result.error(rd.getCode(), rd.getMessage());
        }else {
            DoctorVO vo = DoctorVO.fromDoctor(rd.getData(), false);
            String token = JwtUtil.generateToken(vo.getCode());
            vo.setToken(token);
            return Result.success(vo);
        }
    }
}
