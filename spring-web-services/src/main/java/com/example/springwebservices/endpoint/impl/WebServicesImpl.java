package com.example.springwebservices.endpoint.impl;

import com.example.springwebservices.endpoint.WebServices;
import com.example.springwebservices.model.request.AnimalReqBean;
import com.example.springwebservices.model.response.AbstractResBase;
import com.example.springwebservices.services.AnimalServices;
import jakarta.jws.WebService;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@WebService(endpointInterface = "com.example.springwebservices.endpoint.WebServices")
@Path("/")
@Component
public class WebServicesImpl implements WebServices {

    @Autowired
    private AnimalServices animalServices;

    @Override
    @POST
    @Path("/animal")
    public AbstractResBase animalProcess(AnimalReqBean reqBean) {
        return animalServices.animalProcess(reqBean);

    }

    @Override
    @POST
    @Path("/info")
    public AbstractResBase animalInfo(AnimalReqBean animalReqBean) {
        return animalServices.animalInfo(animalReqBean);
    }

}
