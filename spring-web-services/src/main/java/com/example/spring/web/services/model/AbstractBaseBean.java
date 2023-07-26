package com.example.spring.web.services.model;

import com.example.core.validation.AcceptedValues;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import org.hibernate.validator.constraints.NotBlank;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbstractBaseBean {

    @NotBlank(message = "Date time is mandatory!")
    @XmlElement(name = "Datetime")
    private String dateTime;

    @NotBlank(message = "Version is mandatory!")
    @XmlElement(name = "Version")
    @AcceptedValues(acceptValues = {"1.0", "2.0"}, message = "Invalid Version!")
    private String version;

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
