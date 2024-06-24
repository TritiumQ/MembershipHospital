package me.qunqun.doctor.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.doctor.entity.reps.Result;
import me.qunqun.doctor.entity.vo.PackageVO;
import me.qunqun.doctor.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static me.qunqun.doctor.entity.reps.Result.success;

@Tag(name = "Package", description = "套餐接口")
@Slf4j
@RestController
@RequestMapping("/api/package")
public class PackageController {
    @Autowired
    private PackageService packageService;

    @RequestMapping("/getAll")
    public Result<List<PackageVO>> packageAll() {
        try {
            return packageService.getAll();
        } catch (RuntimeException e) {
            return Result.error(500, "获取套餐类型失败");
        }
    }

}
