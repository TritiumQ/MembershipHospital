package me.qunqun.doctor.repo;

import me.qunqun.shared.entity.po.Doctor;
import me.qunqun.shared.repo.BaseRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DoctorRepository extends BaseRepo<Doctor, Integer> {

    Doctor findByCode(String code);
}