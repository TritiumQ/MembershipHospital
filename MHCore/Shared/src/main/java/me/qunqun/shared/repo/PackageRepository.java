package me.qunqun.shared.repo;

import me.qunqun.shared.entity.po.Package;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PackageRepository extends JpaRepositoryImplementation<Package, Integer>, QuerydslPredicateExecutor<Package>
{
}