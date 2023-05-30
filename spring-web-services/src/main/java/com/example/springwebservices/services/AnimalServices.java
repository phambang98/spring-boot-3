package com.example.springwebservices.services;

import com.example.springcore.utils.DateUtils;
import com.example.springwebservices.model.request.AnimalReqBean;
import com.example.springwebservices.model.response.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AnimalServices {

    ModelMapper modelMapper = new ModelMapper();

    public AbstractResBase animalProcess(AnimalReqBean reqBean) {
        AbstractResBase abstractResBase;
        try {
            abstractResBase = modelMapper.map(reqBean, AbstractResBase.class);
            return abstractResBase;
        } catch (Exception ex) {
            abstractResBase = new AbstractResBase();
            abstractResBase.setVersion("1.0");
            abstractResBase.setDateTime(DateUtils.convertDateToString(new Date(), "dd-mm-yyyy  HH:mm:ss"));
            abstractResBase.setData(new ErrorResponse(ex.getMessage()));
            return abstractResBase;
        }
    }

    public AbstractResBase animalInfo(AnimalReqBean reqBean) {
        AbstractResBase abstractResBase;
        try {
            abstractResBase = modelMapper.map(reqBean, AbstractResBase.class);
            AnimalResBean animalResBean = new AnimalResBean();
            DogResBean dogResBean = new DogResBean();
            CatResBean catResBean = new CatResBean();
            animalResBean.setCatResBean(catResBean);
            animalResBean.setDogResBean(dogResBean);
            abstractResBase.setData(animalResBean);
            return abstractResBase;
        } catch (Exception ex) {
            abstractResBase = new AbstractResBase();
            abstractResBase.setVersion("1.0");
            abstractResBase.setDateTime(DateUtils.convertDateToString(new Date(), "dd-mm-yyyy  HH:mm:ss"));
            abstractResBase.setData(new ErrorResponse(ex.getMessage()));
            return abstractResBase;
        }
    }

    public AbstractResBase abc(String reqBean) {
        AbstractResBase abstractResBase;
        try {
            abstractResBase = modelMapper.map(reqBean, AbstractResBase.class);
            AnimalResBean animalResBean = new AnimalResBean();
            DogResBean dogResBean = new DogResBean();
            CatResBean catResBean = new CatResBean();
            animalResBean.setCatResBean(catResBean);
            animalResBean.setDogResBean(dogResBean);
            abstractResBase.setData(animalResBean);
            return abstractResBase;
        } catch (Exception ex) {
            abstractResBase = new AbstractResBase();
            abstractResBase.setVersion("1.0");
            abstractResBase.setDateTime(DateUtils.convertDateToString(new Date(), "dd-mm-yyyy  HH:mm:ss"));
            abstractResBase.setData(new ErrorResponse(ex.getMessage()));
            return abstractResBase;
        }
    }
}
