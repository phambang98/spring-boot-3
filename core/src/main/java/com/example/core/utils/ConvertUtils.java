package com.example.core.utils;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class ConvertUtils {

    private static Logger logger = LoggerFactory.getLogger(ConvertUtils.class);

    public static <T> T stringXmlToObject(String input, Class<T> clazz) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (T) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        } catch (JAXBException e) {
            logger.error("", e);
        }
        return null;
    }

    public static String objectToStringXml(Object response, String className) {
        try {
            StringWriter sw = new StringWriter();
            JAXBContext jaxbContext = JAXBContext.newInstance(Class.forName(className));
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(response, sw);
            return sw.toString();
        } catch (JAXBException | ClassNotFoundException e) {
            logger.error("", e);
        }
        return null;


    }
}
