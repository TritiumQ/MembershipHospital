package me.qunqun.doctor.repo;

import me.qunqun.shared.entity.po.Order;
import me.qunqun.shared.repo.BaseRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends BaseRepo<Order, Integer> {
    Integer countByHospital_IdAndDeprecated(Integer hospitalId, Boolean deprecated);

    Integer countByHospital_IdAndDateAndDeprecated(Integer hospitalId, LocalDate status, Boolean deprecated);

    Integer countByHospital_IdAndDateBeforeAndDeprecatedAndState(Integer hospitalId, LocalDate date, Boolean deprecated, Integer state);

    Page<Order> findAllByDeprecated(Boolean deprecated, PageRequest pageRequest);

    List<Order> findAllByDateAndDeprecated(LocalDate tomorrow, boolean b);

    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.hospital h " +
            "JOIN FETCH o.user u " +
            "WHERE o.date = :date AND o.deprecated = false")
    List<Order> findByDateAndDeprecated(@Param("date") LocalDate date);
}