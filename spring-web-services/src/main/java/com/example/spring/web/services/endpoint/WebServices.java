package com.example.spring.web.services.endpoint;

import com.example.spring.web.services.model.request.AnimalReqBean;
import com.example.spring.web.services.model.response.AbstractResBase;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.validation.Valid;

@WebService
public interface WebServices {

    @WebMethod
    @WebResult
    AbstractResBase animalProcess(
            @Valid @WebParam(name = "animalProcessRequest") AnimalReqBean animalReqBean)
            throws Exception;

    @WebMethod
    @WebResult
    AbstractResBase animalInfo(
            @Valid @WebParam(name = "animalInfoRequest") AnimalReqBean animalReqBean)
            throws Exception;


}
