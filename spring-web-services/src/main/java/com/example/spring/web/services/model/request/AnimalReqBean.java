package com.example.spring.web.services.model.request;

import com.example.spring.web.services.model.AbstractBaseBean;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Animal")
@XmlAccessorType(XmlAccessType.FIELD)
public class AnimalReqBean extends AbstractBaseBean {

}
