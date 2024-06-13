package me.qunqun.shared.repo;

import me.qunqun.shared.entity.po.Package;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface PackageRepository extends JpaRepositoryImplementation<Package, Integer>
{
}