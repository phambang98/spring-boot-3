package com.example.spring.rest.api.model;

import lombok.Data;

@Data
public class ResultData<T> {
    private String code = "";
    private String message = "";
    private T data;
}
