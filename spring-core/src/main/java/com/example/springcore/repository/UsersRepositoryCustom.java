package com.example.springcore.repository;


import com.example.springcore.model.UsersBean;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersRepositoryCustom {

    List<UsersBean> getAllUsersBean();

    List<String> getByUserName(@Param("senderName") String senderName);
}
