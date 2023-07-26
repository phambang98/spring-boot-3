package com.example.core.repository;


import com.example.core.model.UsersBean;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersRepositoryCustom {

    List<UsersBean> getAllUsersBean();

    List<String> getByUserName(@Param("senderName") String senderName);
}
