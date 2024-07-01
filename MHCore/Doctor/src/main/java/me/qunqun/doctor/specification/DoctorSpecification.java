package me.qunqun.doctor.specification;

import me.qunqun.shared.entity.po.Doctor;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class DoctorSpecification {

    public static Specification<Doctor> getDoctorsByCriteria(Integer doctorId, String code, String name, Integer deptNo, Integer sex, String email) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (doctorId != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), doctorId));
            }
            if (code != null && !code.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("code"), code));
            }
            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("realName"), "%" + name + "%"));
            }
            if (deptNo != null) {
                predicates.add(criteriaBuilder.equal(root.get("deptNo"), deptNo));
            }
            if (sex != null) {
                predicates.add(criteriaBuilder.equal(root.get("sex"), sex));
            }
            if (email != null && !email.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("email"), email));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}