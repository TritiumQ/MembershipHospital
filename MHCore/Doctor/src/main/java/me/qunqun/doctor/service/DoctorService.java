package me.qunqun.doctor.service;

import lombok.extern.slf4j.Slf4j;
import me.qunqun.doctor.annotation.RedisCacheable;
import me.qunqun.doctor.config.ManagerAccountConfig;
import me.qunqun.doctor.entity.vo.DoctorVO;
import me.qunqun.doctor.specification.DoctorSpecification;
import me.qunqun.doctor.utils.ExcelUtils;
import me.qunqun.shared.entity.po.Doctor;
import me.qunqun.doctor.repo.DoctorRepository;
import me.qunqun.doctor.entity.reps.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public Result<Doctor> login(String code, String password, Integer type) {
        if (type == null) {
            return Result.error("登录权限错误");
        } else if (type == 1) {
            if (!code.startsWith("admin") && !password.equals("123456")) {
                return Result.error("管理员登录错误");
            } else {
                Doctor mDoctor = new Doctor();
                mDoctor.setCode(ManagerAccountConfig.code);
                mDoctor.setPassword(ManagerAccountConfig.password);
                mDoctor.setRealName(ManagerAccountConfig.real_name);
                mDoctor.setEmail(ManagerAccountConfig.email);
                mDoctor.setSex(ManagerAccountConfig.sex);
                mDoctor.setDeptNo(ManagerAccountConfig.deptNo);
                return Result.success(mDoctor);
            }
        }
        Doctor doctor = doctorRepository.findByCode(code);
        if (doctor == null) {
            return Result.error("用户名不存在");
        } else if (!doctor.getPassword().equals(password)) {
            return Result.error("密码错误");
        }
        return Result.success(doctor);
    }

    public Result<Boolean> changePassword(String code, String password, String newPassword) {
        Doctor doctor = doctorRepository.findByCode(code);
        if (doctor == null) {
            return Result.error("用户名不存在");
        } else if (!doctor.getPassword().equals(password)) {
            return Result.error("密码错误");
        }
        doctor.setPassword(newPassword);
        doctorRepository.save(doctor);
        return Result.success(true);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Transactional
    public void addDoctor(List<DoctorVO> doctorVOList) {
        List<Doctor> doctors = new ArrayList<>();
        for (DoctorVO doctorVO : doctorVOList) {
            Doctor doctor = new Doctor();
            doctor.setCode(doctorVO.getCode());
            doctor.setPassword(doctorVO.getCode() + "@Doctor");
            doctor.setRealName(doctorVO.getRealName());
            doctor.setEmail(doctorVO.getEmail());
            doctor.setSex(doctorVO.getSex());
            doctor.setDeptNo(doctorVO.getDeptNo());
            doctors.add(doctor);
        }
        doctorRepository.saveAll(doctors);
    }

    @Transactional
    public Boolean delDoctor(List<DoctorVO> doctorVOList) {
        try {
            List<Doctor> doctors = new ArrayList<>();
            for (DoctorVO doctorVO : doctorVOList) {
                Doctor doctor = new Doctor();
//                doctor.setId(doctorRepository.findByCode(doctorVO.getCode()).getId());
                doctor.setId(doctorVO.getId());
                doctors.add(doctor);
            }
            doctorRepository.deleteAll(doctors);
            return true;
        } catch (Exception e) {
            log.error("删除医生账号失败", e);
            return false;
        }
    }

    @Transactional
    public List<DoctorVO> updateDoctor(List<DoctorVO> doctorVOList) {
        try {
            List<Doctor> doctors = new ArrayList<>();
            for (DoctorVO doctorVO : doctorVOList) {
                Doctor doctor = new Doctor();
                doctor.setId(doctorVO.getId());
                doctor.setCode(doctorVO.getCode());
                doctor.setPassword(doctorRepository.findById(doctorVO.getId()).get().getPassword());
                doctor.setRealName(doctorVO.getRealName());
                doctor.setEmail(doctorVO.getEmail());
                doctor.setSex(doctorVO.getSex());
                doctor.setDeptNo(doctorVO.getDeptNo());
                doctors.add(doctor);
            }
            doctorRepository.saveAll(doctors);
            return doctorVOList;
        } catch (Exception e) {
            log.error("更新医生账号失败", e);
            return null;
        }
    }

    @Transactional
    public List<DoctorVO> resetPassword(List<DoctorVO> doctorVOList) {
        try {
            List<Doctor> doctors = new ArrayList<>();
            for (DoctorVO doctorVO : doctorVOList) {
                Doctor doctor = new Doctor();
                doctor.setId(doctorVO.getId());
                doctor.setCode(doctorVO.getCode());
                String password = doctorVO.getCode() + "@Doctor";
                doctor.setPassword(password);
                doctor.setRealName(doctorVO.getRealName());
                doctor.setEmail(doctorVO.getEmail());
                doctor.setSex(doctorVO.getSex());
                doctor.setDeptNo(doctorVO.getDeptNo());
                doctors.add(doctor);
            }
            doctorRepository.saveAll(doctors);
            return doctorVOList;
        } catch (Exception e) {
            log.error("重置医生账号密码失败", e);
            return null;
        }
    }

    @Transactional
    public List<DoctorVO> queryDoctorVO(Integer doctorId, String code, String name, Integer deptNo, Integer sex, String email) {
        try {
            List<Doctor> doctors = doctorRepository.findAll(DoctorSpecification.getDoctorsByCriteria(doctorId,
                    code, name, deptNo, sex, email));
            List<DoctorVO> doctorVOList = new ArrayList<>();
            for (Doctor doctor : doctors) {
                DoctorVO doctorVO = new DoctorVO();
                doctorVO.setId(doctor.getId());
                doctorVO.setCode(doctor.getCode());
                doctorVO.setRealName(doctor.getRealName());
                doctorVO.setEmail(doctor.getEmail());
                doctorVO.setSex(doctor.getSex());
                doctorVO.setDeptNo(doctor.getDeptNo());
                doctorVOList.add(doctorVO);
            }
            return doctorVOList;
        } catch (Exception e) {
            log.error("查询医生账号失败", e);
            return null;
        }
    }

    @Transactional
    public List<DoctorVO> addBatchDoctor(MultipartFile multipartFile) {
        try {
            List<Doctor> doctorList = ExcelUtils.execlToDoctor(multipartFile);
            if (doctorList != null) {
                for (Doctor doctor : doctorList) {
                    if (doctor.getPassword() == null)
                        doctor.setPassword(doctor.getCode() + "@Doctor");
                    doctor.setId(null);
                }
                doctorRepository.saveAll(doctorList);
            }
            List<DoctorVO> doctorVOList = new ArrayList<>();
            if (doctorList != null) {
                for (Doctor doctor : doctorList) {
                    DoctorVO doctorVO = new DoctorVO();
                    doctorVO.setId(doctor.getId());
                    doctorVO.setCode(doctor.getCode());
                    doctorVO.setRealName(doctor.getRealName());
                    doctorVO.setEmail(doctor.getEmail());
                    doctorVO.setSex(doctor.getSex());
                    doctorVO.setDeptNo(doctor.getDeptNo());
                    doctorVOList.add(doctorVO);
                }
                return doctorVOList;
            }else {
                return null;
            }
        } catch (Exception e) {
            log.error("批量导入医生账号失败", e);
            return null;
        }

    }
}
