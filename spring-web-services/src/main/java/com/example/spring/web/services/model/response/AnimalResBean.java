package com.example.spring.web.services.model.response;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Animal")
@XmlAccessorType(XmlAccessType.FIELD)
public class AnimalResBean {

    @XmlElement(name = "Cat",required = true)
    private CatResBean catResBean;

    @XmlElement(name = "Dog")
    private DogResBean dogResBean;

    public CatResBean getCatResBean() {
        return catResBean;
    }

    public void setCatResBean(CatResBean catResBean) {
        this.catResBean = catResBean;
    }

    public DogResBean getDogResBean() {
        return dogResBean;
    }

    public void setDogResBean(DogResBean dogResBean) {
        this.dogResBean = dogResBean;
    }
}
