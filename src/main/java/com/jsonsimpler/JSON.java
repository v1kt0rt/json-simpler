package com.jsonsimpler;

import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class JSON implements Iterable<JSON>, Serializable {
	
	private static final long serialVersionUID = 6673887952929542153L;
	
	private static final Adapter ADAPTER = new JsonSimpleAdapter();
	
	public static JSON from(String s) {
		return s==null ? new JSON() : from(new StringReader(s));
	}
	
	public static JSON from(Reader reader) {
		return new JSON(ADAPTER.parse(reader));
	}
	
	public static JSON array(Object... values) {
		return new JSON().add(values);
	}
	
	public static JSON object() {
		return new JSON(ADAPTER.createObjectInternal());
	}
	
	private Object obj;
	
	public JSON() {
	}
	
	public JSON(Object obj) {
		this.obj = obj;
	}
	
	public JSON get(String key) {
		if(obj==null || !isObject()) {
			return new JSON();
		}
		return new JSON(ADAPTER.getFromObject(obj, key));
	}
	
	public JSON get(int i) {
		if(obj==null) {
			return new JSON();
		}
		if(size()<=i) {
			return new JSON();
		}
		return new JSON(ADAPTER.getFromArray(obj, i));
	}
	
	public boolean isNull() {
		return obj==null;
	}
	
	public boolean isObject() {
		return ADAPTER.isObject(obj);
	}
	
	public boolean isArray() {
		return ADAPTER.isArray(obj);
	}
	
	public boolean isPrimitive() {
		return !isObject() && !isArray();
	}
	
	public boolean isString() {
		return obj instanceof String;
	}
	
	public boolean isLong() {
		return obj instanceof Long;
	}
	
	public boolean isDouble() {
		return obj instanceof Double;
	}
	
	public String asString() {
		return (String)obj;
	}
	
	public Long asLong() {
		if(obj==null) {
			return null;
		}
		if(isLong()) {
			return (Long)obj;
		}
		if(obj instanceof Number) {
			return ((Number)obj).longValue();
		}
		if(obj instanceof String) {
			return Long.parseLong((String)obj);
		}
		throw new RuntimeException("Couldn't get value as Long. Actual type: " + obj.getClass());
	}
	
	public Double asDouble() {
		return (Double)obj;
	}
	
	public Boolean asBoolean() {
		if(obj==null) {
			return null;
		}
		if(obj instanceof Boolean) {
			return (Boolean)obj;
		}
		if(isString()) {
			return Boolean.parseBoolean((String)obj);
		}
		if(obj instanceof Number) {
			return ((Number)obj).intValue()>0;
		}
		if(isArray() || isObject()) {
			return size()>0;
		}
		throw new RuntimeException("Couldn't get a value as Boolean. Actual type:" + obj.getClass());
	}
	
	@Deprecated
	public Object getRawObject() {
		return obj;
	}
	
	/**
	 * Returns the ith element of an array as string.
	 * Throws ClassCastException when the ith element asn't a string.
	 * @param i Index in array, zero based.
	 * @return ith element or null of the size of the array is smaller.
	 */
	public String getAsString(int i) {
		return get(i).asString();
	}
	
	public String getAsString(String key) {
		return get(key).asString();
	}
	
	public String getAsString(String key, String defaultValue) {
		String value = getAsString(key);
		return value==null ? defaultValue : value;
	}

	public Boolean getAsBoolean(String key) {
		return get(key).asBoolean();
	}
	
	public boolean getAsBoolean(String key, boolean defaultValue) {
		Boolean value = getAsBoolean(key);
		return value==null ? defaultValue : value;
	}
	
	public Long getAsLong(String key) {
		return get(key).asLong();
	}
	
	public long getAsLong(String key, long defaultValue) {
		Long value = getAsLong(key);
		return value==null ? defaultValue : value;
	}
	
	public Double getAsDouble(String key) {
		return get(key).asDouble();
	}
	
	public String toJSONString() {
		if(isObject()) {
			return ADAPTER.objectToJSONString(obj);
		} else if(isArray()) {
			return ADAPTER.arrayToJSONString(obj);
		}
		return obj==null ? "null" : obj.toString();
	}
	
	public Set<String> keySet() {
		Set<String> result = new HashSet<>();
		if(isObject()) {
			for(Object o : ADAPTER.keySet(obj)){
				result.add(o==null ? null : o.toString());
			}
		}
		return result;
	}
	
	public Collection<JSON> values() {
		Collection<JSON> result = new ArrayList<>();
		if(!isNull() && isPrimitive()) {
			result.add(this);
		} else if(isObject()) { 
			for(Object item : ADAPTER.values(obj)) {
				result.add(new JSON(item));
			}
		} else if(isArray()) {
			for(int i=0;i<ADAPTER.sizeOfArray(obj);i++) {
				result.add(get(i));
			}
		}
		return result;
	}

	@Override
	public Iterator<JSON> iterator() {
		if(isNull() || isPrimitive()) {
			return new SingleItemIterator(this);
		}
		return new ArrayIterator(isArray() ? this : JSON.array());
	}
	
	public JSON put(String key, Object value) {
		if(obj==null) {
			obj = ADAPTER.createObjectInternal();
		}
		ADAPTER.putIntoObject(obj, key, unwrap(value));
		return this;
	}
	
	public JSON putIf(boolean condition, String key, Object value) {
		return condition ? put(key, value) : this;
	}
	
	public JSON remove(String... keys) {
		if(obj==null) {
			return new JSON();
		}
		for(String key : keys) {
			ADAPTER.removeFromObject(obj, key);
		}
		return this;
	}
	
	public JSON add(Object... values) {
		if(obj==null) {
			obj = ADAPTER.createArrayInternal();
		}
		for(Object value : values) {
			ADAPTER.addToArray(obj, unwrap(value));
		}
		return this;
	}
	
	public JSON add(int index, Object value) {
		if(obj==null) {
			obj = ADAPTER.createArrayInternal();
		}
		ADAPTER.addToArray(obj, unwrap(value), index);
		return this;
	}

	private Object unwrap(Object value) {
		while(value instanceof JSON) {
			value = ((JSON) value).obj;
		}
		return value;
	}
	
	public int size() {
		if(isNull()) {
			return 0;
		}
		if(isArray()) {
			return ADAPTER.sizeOfArray(obj);
		}
		if(isObject()) {
			return ADAPTER.sizeOfObject(obj);
		}
		return 1;
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof JSON)) {
			return false;
		}
		JSON otherJson = (JSON) other;
		if(obj==null) {
			return otherJson.obj==null;
		}
		return obj.equals(otherJson.obj);
	}
	
	@Override
	public int hashCode() {
		return obj==null ? 0 : obj.hashCode();
	}
	
	public JSON deepClone() {
		return JSON.from(toJSONString());
	}
}