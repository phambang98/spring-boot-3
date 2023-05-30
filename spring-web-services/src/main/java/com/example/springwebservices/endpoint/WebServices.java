package com.example.springwebservices.endpoint;

import com.example.springwebservices.model.request.AnimalReqBean;
import com.example.springwebservices.model.response.AbstractResBase;
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
