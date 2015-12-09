package com.bitscanvas.thehytt.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

public class JSONConvertor {

	public static Object jsonToObject(String json, Class objClass) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		Object obj = mapper.readValue(json,  objClass);
		return obj;
	}
	
	public static String objectToJson(Object object){
		String res = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			StringWriter writer = new StringWriter();
			mapper.writeValue(writer, object);
			res =writer.getBuffer().toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
}
