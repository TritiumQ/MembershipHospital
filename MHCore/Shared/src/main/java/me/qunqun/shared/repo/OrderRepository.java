package me.qunqun.shared.repo;

import me.qunqun.shared.entity.po.Order;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface OrderRepository extends JpaRepositoryImplementation<Order, Integer>
{
}