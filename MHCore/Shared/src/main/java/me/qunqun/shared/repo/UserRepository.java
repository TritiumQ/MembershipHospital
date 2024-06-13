package me.qunqun.shared.repo;

import me.qunqun.shared.entity.po.User;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface UserRepository extends JpaRepositoryImplementation<User, String>
{
}