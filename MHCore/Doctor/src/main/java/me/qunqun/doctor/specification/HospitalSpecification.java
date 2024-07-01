package me.qunqun.doctor.specification;

import jakarta.persistence.criteria.Predicate;
import me.qunqun.shared.entity.po.Hospital;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class HospitalSpecification {

    public static Specification<Hospital> getHospitalsByCriteria(Integer hospitalId, String name, String address, String phone) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (hospitalId != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), hospitalId));
            }
            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            if (address != null && !address.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("address"), "%" + address + "%"));
            }
            if (phone != null && !phone.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("phone"), phone));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
