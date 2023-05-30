package com.example.springcore.repository;

import com.example.springcore.model.FriendProfileModel;
import com.example.springcore.model.StatusModel;

import java.util.List;

public interface ChatRepositoryCustom {

    List<FriendProfileModel> getFriendList(Long userId);

    List<StatusModel> getFriendStatusByUserId(Long userId);

    List<Long> getFriendIdByUserId(Long userId);

}
