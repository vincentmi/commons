package com.vnzmi.commons.data;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.Nullable;

import java.util.List;

@NoRepositoryBean
public interface PhiloRepository<T, ID>  extends JpaRepository<T, ID>  , JpaSpecificationExecutor<T> {
    List<T> fetchPage(@Nullable Specification<T> spec);
}
