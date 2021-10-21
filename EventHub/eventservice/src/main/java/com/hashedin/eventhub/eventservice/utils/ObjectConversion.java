package com.hashedin.eventhub.eventservice.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ObjectConversion {

    @Autowired
    private ModelMapper modelMapper;

    public Object convert(Object object, Class<?> type) {

        return modelMapper.map(object,type);
    }

}


