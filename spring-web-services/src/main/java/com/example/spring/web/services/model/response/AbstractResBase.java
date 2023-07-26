package com.example.spring.web.services.model.response;

import com.example.spring.web.services.model.AbstractBaseBean;
import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "SOAP")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({AnimalResBean.class, ErrorResponse.class})
public class AbstractResBase<T> extends AbstractBaseBean {

    @XmlElement(name = "Data")
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
