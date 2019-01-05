package com.aptech.business.util;

import java.io 

.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


public class JsonDateDeserializer  extends JsonDeserializer<Date>{

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        try {
			return simpleDateFormat.parse(jp.getText());
		} catch (ParseException e) {
			return new Date(jp.getLongValue());
		}
	}

}