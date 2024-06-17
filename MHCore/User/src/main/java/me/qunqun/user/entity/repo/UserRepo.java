package me.qunqun.user.entity.repo;

import me.qunqun.shared.entity.po.User;
import me.qunqun.shared.repo.BaseRepo;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepo extends BaseRepo<User, String>, QuerydslPredicateExecutor<User>
{
}
