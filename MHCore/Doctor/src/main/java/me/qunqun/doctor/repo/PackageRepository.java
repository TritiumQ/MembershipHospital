package me.qunqun.doctor.repo;

import me.qunqun.shared.entity.po.Package;
import me.qunqun.shared.repo.BaseRepo;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public interface PackageRepository extends BaseRepo<Package, Integer> {
    @NotNull List<Package> findAll();

}