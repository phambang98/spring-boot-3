package com.example.springcore.repository;

import com.example.springcore.entity.Users;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

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

}
