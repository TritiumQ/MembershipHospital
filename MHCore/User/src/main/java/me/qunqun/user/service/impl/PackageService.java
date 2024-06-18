package me.qunqun.user.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Resource;
import me.qunqun.shared.entity.po.QPackage;
import me.qunqun.user.entity.vo.PackageInfoVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageService implements me.qunqun.user.service.IPackageService
{
	@Resource
	private JPAQueryFactory qFactory;
	
	@Override
	public List<PackageInfoVo> list(Integer type)
	{
		var qPackage = QPackage.package$;
		var query = qFactory.selectFrom(qPackage).where(qPackage.type.eq(type));
		return query.fetch().stream().map(PackageInfoVo::new).toList();
	}
	
	@Override
	public PackageInfoVo get(Integer id)
	{
		var qPackage = QPackage.package$;
		var query = qFactory.selectFrom(qPackage).where(qPackage.id.eq(id));
		var po = query.fetchOne();
		return new PackageInfoVo(po);
	}
	
}
