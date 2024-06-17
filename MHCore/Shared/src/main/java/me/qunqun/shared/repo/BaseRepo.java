package me.qunqun.shared.repo;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepo<T, TID>
	extends JpaRepositoryImplementation<T, TID>
{

}
