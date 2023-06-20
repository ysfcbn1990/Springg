package com.tpe.repository;

import com.tpe.domain.Role;
import com.tpe.domain.enums.RoleType;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {


    Optional<Role> findByType(RoleType type);
}
