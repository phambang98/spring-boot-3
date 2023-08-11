package com.example.spring.rest.api.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class UserChatGroupModel {

    @NotBlank(message = "List User Name cannot not blank")
    private List<@Valid String> listUserName;

    @NotBlank(message = "Chat Id cannot not blank")
    private Long chatId;
}
