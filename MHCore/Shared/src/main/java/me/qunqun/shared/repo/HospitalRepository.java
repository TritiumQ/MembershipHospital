package me.qunqun.shared.repo;

import me.qunqun.shared.entity.po.Hospital;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface HospitalRepository extends JpaRepositoryImplementation<Hospital, Integer>
{
}