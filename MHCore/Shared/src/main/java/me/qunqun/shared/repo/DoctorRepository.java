package me.qunqun.shared.repo;

import me.qunqun.shared.entity.po.Doctor;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface DoctorRepository extends JpaRepositoryImplementation<Doctor, Integer>
{
}