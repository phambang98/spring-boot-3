package com.example.spring.rest.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddCreditModel {

    @NotBlank(message = "Amount money cannot not blank")
    private Long amount;
}
