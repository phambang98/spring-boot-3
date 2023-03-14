package com.example.repository;

import com.example.model.ClientBean;

import java.util.List;

public interface CustomClientRepository {

    List<ClientBean> findAbc();

    List<ClientBean> getAllClientBean();
}
