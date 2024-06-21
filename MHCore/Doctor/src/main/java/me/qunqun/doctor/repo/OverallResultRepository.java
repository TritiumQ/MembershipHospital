package me.qunqun.doctor.repo;

import me.qunqun.shared.entity.po.OverallResult;
import me.qunqun.shared.repo.BaseRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OverallResultRepository extends BaseRepo<OverallResult, Integer> {

    List<OverallResult> findByOrderId(Integer orderId);

    void deleteByOrderId(Integer id);
}