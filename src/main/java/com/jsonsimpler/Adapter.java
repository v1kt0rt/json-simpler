package com.jsonsimpler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

class Adapter {
	
	boolean isObject(Object obj) {
		return obj instanceof JSONObject;
	}
	
	boolean isArray(Object obj) {
		return obj instanceof JSONArray;
	}
	
	Object createArrayInternal() {
		return new JSONArray();
	}
	
	Object createObjectInternal() {
		return new JSONObject();
	}

}
