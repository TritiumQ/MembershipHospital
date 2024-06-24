package me.qunqun.doctor.service;

import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.po.Doctor;
import me.qunqun.doctor.repo.DoctorRepository;
import me.qunqun.doctor.entity.reps.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public Result<Doctor> login(String code, String password) {
        Doctor doctor = doctorRepository.findByCode(code);
        if (doctor == null) {
            return Result.error("用户名不存在");
        } else if (!doctor.getPassword().equals(password)) {
            return Result.error("密码错误");
        }
        return Result.success(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
}
