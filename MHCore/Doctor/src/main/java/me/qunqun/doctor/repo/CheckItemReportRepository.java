package me.qunqun.doctor.repo;

import me.qunqun.shared.entity.po.CheckItemReport;
import me.qunqun.shared.repo.BaseRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CheckItemReportRepository extends BaseRepo<CheckItemReport, Integer> {

    List<CheckItemReport> findByOrderId(Integer orderId);
}