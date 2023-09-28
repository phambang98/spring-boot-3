package com.example.spring.rest.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CaptchaRp {
    private String captchaImg;
    private String captchaId;
}
