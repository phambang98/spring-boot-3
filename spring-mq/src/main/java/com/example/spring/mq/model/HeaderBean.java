package com.example.spring.mq.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;
import java.util.Date;

@XmlRootElement(name = "Header")
@XmlAccessorType(XmlAccessType.FIELD)
public class HeaderBean implements Serializable {


    @XmlElement(name = "Mq")
    private String mq;

    @XmlElement(name = "DateTime")
    private String dateTime;

    @XmlElement(name = "Version")
    private String version;

    public String getMq() {
        return mq;
    }

    public void setMq(String mq) {
        this.mq = mq;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
