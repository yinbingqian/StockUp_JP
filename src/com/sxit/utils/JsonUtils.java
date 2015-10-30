package com.sxit.utils;

import java.util.Collection;
import com.google.gson.Gson;

public class JsonUtils {
	/**
	 * 
	 * @param object
	 * @return
	 */
	public static String beanToJson(Object object) {
		Gson gson = new Gson();
		String json = gson.toJson(object);
		return json;
	}

	/**
	 * 
	 * @param json
	 * @param c
	 * @return
	 */
	public static Object jsonToBean(String json, Class<?> c) {
		Gson gson = new Gson();
		Object object = gson.fromJson(json, c);
		return object;
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	public static String listToJson(Collection<?> list) {
		Gson gson = new Gson();
		String json = gson.toJson(list);
		return json;
	}



}
