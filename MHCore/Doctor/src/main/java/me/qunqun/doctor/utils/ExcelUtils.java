package me.qunqun.doctor.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.po.Doctor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
public class ExcelUtils {

    // 临时文件夹
    final static String TEMP_PATH = System.getProperty("java.io.tmpdir");

    // MultipartFile 转 File
    public static File multipartFileToFile(MultipartFile file) throws IOException {
        File toFile = new File(TEMP_PATH + '/' + file.getOriginalFilename());
        file.transferTo(toFile);
        return toFile;
    }

    /**
     * 文件校验
     */
    public static boolean checkFile(MultipartFile file) {
        // 判断文件是否为空
        if (file == null) {
            log.error("文件为空");
            return false;
        }
        // 文件大小限制
        if (file.getSize() > 1024 * 1024 * 10) {
            log.error("文件大小超过10M");
            return false;
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 判断文件是否为excel文件
        if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) {
            log.error(fileName + "不是excel文件");
            return false;
        }
        return true;
    }

    public static List<Doctor> execlToDoctor(MultipartFile multipartFile) {
        File file = null;
        try {
            file = ExcelUtils.multipartFileToFile(multipartFile);
            ExcelReader reader = ExcelUtil.getReader(FileUtil.file(file), 0);
            List<Map<String, Object>> readAll = reader.readAll();
            List<Doctor> DoctorList = new ArrayList<>();
            for (Map<String, Object> map : readAll) {
                Doctor Doctor = new Doctor();
//                Doctor.setId(Integer.parseInt(map.get("doctorId").toString()));
                Doctor.setCode(map.get("doctorCode").toString());
                Doctor.setRealName(map.get("doctorName").toString());
                Doctor.setSex(Integer.parseInt(map.get("doctorSex").toString()));
                Doctor.setDeptNo(Integer.parseInt(map.get("deptNo").toString()));
                Doctor.setEmail(map.get("email").toString());
                DoctorList.add(Doctor);
            }
            if (file != null) {
                FileUtil.del(file);
            }
            return DoctorList;
        } catch (IOException e) {
            log.error("MultipartFile 文件处理失败");
            e.printStackTrace();
        } finally {
            if (file != null) {
                FileUtil.del(file);
            }
        }
        return null;
    }
}
