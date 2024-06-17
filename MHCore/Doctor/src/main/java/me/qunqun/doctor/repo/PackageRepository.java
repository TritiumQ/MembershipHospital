package me.qunqun.doctor.repo;

import me.qunqun.shared.entity.po.Package;
import me.qunqun.shared.repo.BaseRepo;
import org.apache.poi.sl.usermodel.Background;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PackageRepository extends BaseRepo<Package, Integer> {

}