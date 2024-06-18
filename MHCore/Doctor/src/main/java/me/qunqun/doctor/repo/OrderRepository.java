package me.qunqun.doctor.repo;

import me.qunqun.shared.entity.po.Order;
import me.qunqun.shared.repo.BaseRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.Date;

public interface OrderRepository extends BaseRepo<Order, Integer> {
    Integer countByHospital_IdAndDeprecated(Integer hospitalId, Boolean deprecated);

    Integer countByHospital_IdAndDateAndDeprecated(Integer hospitalId, LocalDate status, Boolean deprecated);

    Page<Order> findAllByDeprecated(Boolean deprecated, PageRequest pageRequest);
}