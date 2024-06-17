package me.qunqun.shared.repo;

import me.qunqun.shared.entity.po.CheckItem;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CheckItemRepository extends JpaRepositoryImplementation<CheckItem, Integer>
{
}