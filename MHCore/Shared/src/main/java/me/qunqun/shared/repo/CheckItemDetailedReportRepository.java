package me.qunqun.shared.repo;

import me.qunqun.shared.entity.po.CheckItemDetailedReport;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CheckItemDetailedReportRepository extends JpaRepositoryImplementation<CheckItemDetailedReport, Integer>
{
}