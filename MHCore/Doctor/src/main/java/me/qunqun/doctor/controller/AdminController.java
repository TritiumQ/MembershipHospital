package me.qunqun.doctor.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.doctor.entity.reps.Result;
import me.qunqun.doctor.entity.vo.DoctorVO;
import me.qunqun.doctor.entity.vo.HospitalVO;
import me.qunqun.doctor.service.DoctorService;
import me.qunqun.doctor.service.HospitalService;
import me.qunqun.doctor.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private HospitalService hospitalService;

    @Operation(summary = "医生账号增加", description = "医生账号增加接口")
    @PostMapping("/addDoctor")
    public Result<List<DoctorVO>> addDoctor(@RequestBody DoctorVO doctorVO) {
        if(doctorVO == null)
            return Result.success(null);
        List<DoctorVO> doctorVOList = new ArrayList<>();
        doctorVOList.add(doctorVO);
        doctorService.addDoctor(doctorVOList);
        return Result.success(doctorVOList);
    }

    @Operation(summary = "医生账号批量增加", description = "医生账号批量增加接口")
    @PostMapping("/addBatchDoctor")
    public Result<List<DoctorVO>> addBatchDoctor(@RequestPart("file") MultipartFile multipartFile) {
        // 文件校验
        if (!ExcelUtils.checkFile(multipartFile)){
            return Result.error("文件格式错误");
        }
        List<DoctorVO> doctorVOList = doctorService.addBatchDoctor(multipartFile);
        if (doctorVOList == null){
            return Result.error("医生信息批量导入失败");
        }
        return Result.success(doctorVOList);
    }

    @Operation(summary = "医生账号删除", description = "医生账号删除接口")
    @PostMapping("/delDoctor")
    public Result<Boolean> delDoctor(@RequestBody List<DoctorVO> doctorVOList) {

        Boolean result = doctorService.delDoctor(doctorVOList);
        if (result) {
            return Result.success(result);
        } else {
            return Result.error("医生信息删除失败");
        }
    }


    @Operation(summary = "医生账号修改", description = "医生账号修改接口")
    @PostMapping("/updateDoctor")
    public Result<List<DoctorVO>> updateDoctor(@RequestBody List<DoctorVO> doctorVOList) {
        List<DoctorVO> result = doctorService.updateDoctor(doctorVOList);
        if (result != null) {
            return Result.success(result);
        } else {
            return Result.error("医生信息修改失败");
        }
    }

    @Operation(summary = "医生账号重置密码", description = "医生账号重置密码接口")
    @PostMapping("/resetDoctorPassword")
    public Result<List<DoctorVO>> resetDoctorPassword(@RequestBody List<DoctorVO> doctorVOList) {
        List<DoctorVO> result = doctorService.resetPassword(doctorVOList);
        if (result != null) {
            return Result.success(result);
        } else {
            return Result.error("医生密码重置失败");
        }
    }

    @Operation(summary = "医生账号查询", description = "医生账号查询接口")
    @GetMapping("/queryDoctorVO")
    public Result<List<DoctorVO>> queryDoctorVO(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String realName,
            @RequestParam(required = false) Integer deptNo,
            @RequestParam(required = false) Integer sex,
            @RequestParam(required = false) String email) {
        List<DoctorVO> doctorVOList = doctorService.queryDoctorVO(id, code, realName, deptNo, sex, email);
        if (doctorVOList != null) {
            return Result.success(doctorVOList);
        } else {
            return Result.error("医生信息查询失败");
        }
    }


    @Operation(summary = "医院信息查询", description = "医院信息查询接口")
    @GetMapping("/queryHospitalVO")
    public Result<List<HospitalVO>> queryHospitalVO(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String phone) {
        List<HospitalVO> hospitalVOList = hospitalService.queryHospitalVO(id, name, address, phone);
        if (hospitalVOList != null) {
            return Result.success(hospitalVOList);
        } else {
            return Result.error("医院信息查询失败");
        }
    }

}
