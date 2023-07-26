package com.example.core.repository;

import com.example.core.entity.Users;
import jakarta.persistence.LockModeType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long>, UsersRepositoryCustom {
    Users findByUserName(String userName);

    Users findByUserNameAndProviderEqualsIgnoreCase(String userName, String provider);

    Users findByEmail(String email);

    boolean existsByUserName(String userName);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Users> findByIdIsIn(List<Long> ids);

    Boolean existsByEmail(String email);

    List<Users> findByUserNameIn(List<String> listUserName);

    @Cacheable(value = "users", key = "T(java.lang.String).format('%s', #p0)")
    List<Users> findByEmailIn(List<String> listEmail);
}
