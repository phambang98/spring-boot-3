package com.example.springwebservices.model.request;

import com.example.springwebservices.model.AbstractBaseBean;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Animal")
@XmlAccessorType(XmlAccessType.FIELD)
public class AnimalReqBean extends AbstractBaseBean {

}
