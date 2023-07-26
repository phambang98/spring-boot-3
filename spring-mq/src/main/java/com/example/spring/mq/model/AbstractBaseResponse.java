package com.example.spring.mq.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement(name = "Data")
@XmlAccessorType(XmlAccessType.FIELD)
public class AbstractBaseResponse<T> implements Serializable {
    @XmlElement(name = "Header")
    private HeaderBean header;

    @XmlElement(name = "Body")
    private T body;
    @XmlElement(name = "Footer")
    private FooterBean footer;

    public AbstractBaseResponse() {
    }

    public AbstractBaseResponse(HeaderBean header, T body, FooterBean footer) {
        this.header = header;
        this.body = body;
        this.footer = footer;
    }

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public FooterBean getFooter() {
        return footer;
    }

    public void setFooter(FooterBean footer) {
        this.footer = footer;
    }
}
