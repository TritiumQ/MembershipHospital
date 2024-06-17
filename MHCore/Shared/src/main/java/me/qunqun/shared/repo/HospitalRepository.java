package me.qunqun.shared.repo;

import me.qunqun.shared.entity.po.Hospital;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface HospitalRepository extends JpaRepositoryImplementation<Hospital, Integer>
{
}