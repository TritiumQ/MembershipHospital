package me.qunqun.doctor.repo;

import me.qunqun.shared.entity.po.Order;
import me.qunqun.shared.repo.BaseRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.Date;

public interface OrderRepository extends BaseRepo<Order, Integer> {
    Integer countByHospital_Id(Integer hospitalId);

    Integer countByHospital_IdAndDate(Integer hospitalId, LocalDate status);
}