package com.example.springbatch.batch.mapper;

import com.example.springcore.model.TradeBean;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class TradeConverter implements Converter {
    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {

    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext unmarshallingContext) {
        Map<String, String> map = Collections.emptyMap();
        while (reader.hasMoreChildren()) {
            if (map == Collections.EMPTY_MAP) {
                map = new HashMap<>();
            }
            reader.moveDown();
            String key = reader.getNodeName();
            String value = reader.getValue();
            reader.moveUp();
            map.put(key, value);
        }
        return new TradeBean("", 1, 1d, "", "", "");
    }

    @Override
    public boolean canConvert(Class type) {
        return type.equals(TradeBean.class);
    }
}
