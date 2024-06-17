package me.qunqun.doctor.repo;

import me.qunqun.shared.entity.po.User;
import me.qunqun.shared.repo.BaseRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends BaseRepo<User, Integer> {

}