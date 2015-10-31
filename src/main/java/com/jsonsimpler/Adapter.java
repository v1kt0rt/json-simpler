package com.jsonsimpler;

import java.io.Reader;
import java.util.Set;

interface Adapter {

	Object parse(Reader reader);

	boolean isObject(Object obj);

	Object createObjectInternal();

	Object getFromObject(Object obj, String key);

	int sizeOfObject(Object obj);

	void putIntoObject(Object obj, String key, Object value);

	void removeFromObject(Object obj, String key);

	Set<Object> keySet(Object obj);

	String objectToJSONString(Object obj);

	boolean isArray(Object obj);

	Object createArrayInternal();

	int sizeOfArray(Object obj);

	Object getFromArray(Object array, int i);

	String arrayToJSONString(Object obj);

	void addToArray(Object obj, Object value);

	void addToArray(Object obj, Object value, int index);

}