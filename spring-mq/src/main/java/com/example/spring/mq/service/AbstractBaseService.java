package com.example.spring.mq.service;

import com.example.core.utils.DateUtils;
import com.example.spring.mq.constants.KeyMQ;
import com.example.spring.mq.model.AbstractBaseRequest;
import com.example.spring.mq.model.AbstractBaseResponse;
import com.example.spring.mq.model.FooterBean;
import com.example.spring.mq.model.HeaderBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public abstract class AbstractBaseService {

    Logger logger = LoggerFactory.getLogger(AbstractBaseService.class);


    protected boolean processHeader(AbstractBaseRequest request, AbstractBaseResponse response) {
        HeaderBean headerBean;
        if ((headerBean = request.getHeader()) == null) {
            headerBean = new HeaderBean();
            headerBean.setDateTime(DateUtils.convertDateToString(new Date(), "dd/MM/yyyy hh:mm:ss"));
            headerBean.setVersion("1.0");
            response.setHeader(headerBean);
            response.setBody("Header is mandatory");
            logger.error("Header is mandatory");
            return false;

        }
        if (StringUtils.isBlank(headerBean.getDateTime()) && (DateUtils.convertStringtoDate(headerBean.getDateTime(), "dd/MM/yyyy hh:mm:ss") == null)) {
            headerBean.setDateTime(DateUtils.convertDateToString(new Date(), "dd/MM/yyyy hh:mm:ss"));
            response.setHeader(headerBean);
            response.setBody("DateTime invalid request");
            return false;
        }

        HeaderBean finalHeaderBean = headerBean;
        if (!KeyMQ.LIST_MQ.stream().anyMatch(x->x.equals(finalHeaderBean.getVersion()))) {
            headerBean.setVersion(KeyMQ.VERSION);
            response.setHeader(headerBean);
            response.setBody("Version invalid request");
            logger.error("Header is mandatory");
            return false;
        }

        if (!KeyMQ.VERSION.equals(headerBean.getVersion())) {
            headerBean.setVersion(KeyMQ.VERSION);
            response.setHeader(headerBean);
            response.setBody("Version invalid request");
            logger.error("Header is mandatory");
            return false;
        }
        return true;

    }

    protected boolean processFooter(AbstractBaseRequest request, AbstractBaseResponse response) {
        FooterBean footerBean;
        if ((footerBean = request.getFooter()) == null) {
            footerBean = new FooterBean();
            footerBean.setInfo("abc");
            response.setFooter(footerBean);
            response.setBody("Footer is mandatory");
            logger.error("Header is mandatory");
            return false;
        }
        if (!KeyMQ.INFO.equals(footerBean.getInfo())) {
            footerBean.setInfo(KeyMQ.INFO);
            response.setFooter(footerBean);
            response.setBody("Info invalid");
            logger.error("Header is mandatory");
            return false;
        }
        return true;
    }


    public abstract AbstractBaseResponse process(AbstractBaseRequest target);
}
