package me.qunqun.shared.repo;

import me.qunqun.shared.entity.po.CheckItemReport;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CheckItemReportRepository extends JpaRepositoryImplementation<CheckItemReport, Integer>
{
}