package com.example.springwebservices.model.response;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ErrorRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorResponse {

    @XmlElement(name = "Code")
    private String code = "00";

    @XmlElement(name = "Message")
    private String message;

    public String getCode() {
        return code;
    }

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse() {
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
