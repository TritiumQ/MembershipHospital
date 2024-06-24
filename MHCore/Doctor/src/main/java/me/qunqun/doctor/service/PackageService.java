package me.qunqun.doctor.service;

import me.qunqun.doctor.annotation.RedisCacheable;
import me.qunqun.doctor.entity.reps.Result;
import me.qunqun.doctor.entity.vo.OrderVO;
import me.qunqun.doctor.entity.vo.PackageVO;
import me.qunqun.doctor.repo.PackageRepository;
import me.qunqun.shared.entity.po.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageService {

    @Autowired
    private PackageRepository packageRepository;

    @RedisCacheable(keyPrefix = "packages")
    public Result<List<PackageVO>> getAll() {
        try {
            List<Package> packages = packageRepository.findAll();
            List<PackageVO> packageVOS = packages.stream().map(packageItem -> {
                PackageVO packageVO = new PackageVO();
                packageVO.setId(packageItem.getId());
                packageVO.setName(packageItem.getName());
                packageVO.setPrice(packageItem.getPrice());
                packageVO.setType(packageItem.getType());
                return packageVO;
            }).toList();
            return Result.success(packageVOS);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while getting packages: " + e.getMessage());
        }
    }

}
