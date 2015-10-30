package com.jsonsimpler;

import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

class Adapter {
	
	boolean isObject(Object obj) {
		return obj instanceof JSONObject;
	}
	
	Object createObjectInternal() {
		return new JSONObject();
	}
	
	Object getFromObject(Object obj, String key) {
		return ((JSONObject)obj).get(key);
	}
	
	int sizeOfObject(Object obj) {
		return ((JSONObject)obj).size();
	}
	
	@SuppressWarnings("unchecked")
	void putIntoObject(Object obj, String key, Object value) {
		((JSONObject) obj).put(key, value);
	}
	
	void removeFromObject(Object obj, String key) {
		((JSONObject) obj).remove(key);
	}
	
	@SuppressWarnings("unchecked")
	Set<Object> keySet(Object obj) {
		return ((JSONObject)obj).keySet();
	}
	
	String objectToJSONString(Object obj) {
		return ((JSONObject) obj).toJSONString();
	}
	
	boolean isArray(Object obj) {
		return obj instanceof JSONArray;
	}
	
	Object createArrayInternal() {
		return new JSONArray();
	}
	
	Object getFromArray(Object array, int i) {
		return ((JSONArray)array).get(i);
	}
}
