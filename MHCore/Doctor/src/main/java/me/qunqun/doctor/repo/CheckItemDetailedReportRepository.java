package me.qunqun.doctor.repo;

import me.qunqun.shared.entity.po.CheckItemDetailedReport;
import me.qunqun.shared.repo.BaseRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CheckItemDetailedReportRepository extends BaseRepo<CheckItemDetailedReport, Integer> {

    List<CheckItemDetailedReport> findByOrderId(Integer orderId);
}