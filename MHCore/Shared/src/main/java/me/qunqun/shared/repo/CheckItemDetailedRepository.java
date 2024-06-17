package me.qunqun.shared.repo;

import me.qunqun.shared.entity.po.CheckItemDetailed;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CheckItemDetailedRepository extends JpaRepositoryImplementation<CheckItemDetailed, Integer>, QuerydslPredicateExecutor<CheckItemDetailed>
{
}