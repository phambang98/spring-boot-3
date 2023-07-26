package com.example.spring.web.services.endpoint.impl;

import com.example.spring.web.services.endpoint.WebServices;
import com.example.spring.web.services.model.request.AnimalReqBean;
import com.example.spring.web.services.model.response.AbstractResBase;
import com.example.spring.web.services.services.AnimalServices;
import jakarta.jws.WebService;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@WebService(endpointInterface = "com.example.spring.web.services.endpoint.WebServices")
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
