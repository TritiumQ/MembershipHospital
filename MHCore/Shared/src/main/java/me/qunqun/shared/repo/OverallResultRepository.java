package me.qunqun.shared.repo;

import me.qunqun.shared.entity.po.OverallResult;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OverallResultRepository extends JpaRepositoryImplementation<OverallResult, Integer>, QuerydslPredicateExecutor<OverallResult>
{
}