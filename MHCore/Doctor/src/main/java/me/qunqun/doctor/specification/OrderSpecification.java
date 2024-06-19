package me.qunqun.doctor.specification;


import jakarta.persistence.criteria.Predicate;
import me.qunqun.doctor.entity.dto.OrderQueryDTO;
import me.qunqun.shared.entity.po.Order;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OrderSpecification {

    public static Specification<Order> getOrders(OrderQueryDTO orderQueryDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (orderQueryDTO.getOrderId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), orderQueryDTO.getOrderId()));
            }
            if (orderQueryDTO.getUserName() != null) {
                predicates.add(criteriaBuilder.like(root.join("user").get("realName"), "%" + orderQueryDTO.getUserName() + "%"));
            }
            if (orderQueryDTO.getUserSex() != null) {
                predicates.add(criteriaBuilder.equal(root.join("user").get("sex"), orderQueryDTO.getUserSex()));
            }
            if (orderQueryDTO.getHospitalId() != null) {
                predicates.add(criteriaBuilder.equal(root.join("hospital").get("id"), orderQueryDTO.getHospitalId()));
            }
            if (orderQueryDTO.getOrderDate() != null) {
                predicates.add(criteriaBuilder.equal(root.get("date"), orderQueryDTO.getOrderDate()));
            }
            if (orderQueryDTO.getPackageId() != null) {
                predicates.add(criteriaBuilder.equal(root.join("packageInfo").get("id"), orderQueryDTO.getPackageId()));
            }
            if (orderQueryDTO.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("state"), orderQueryDTO.getStatus()));
            }
            predicates.add(criteriaBuilder.equal(root.get("deprecated"), false));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
