package com.jsonsimpler;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class JSON implements Iterable<JSON> {
	
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
	
	public boolean isString() {
		return obj instanceof String;
	}
	
	public String asString() {
		return (String)obj;
	}
	
	public Long asLong() {
		return (Long)obj;
	}
	
	public Boolean asBoolean() {
		return (Boolean)obj;
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
	
	public Boolean getAsBoolean(String key) {
		return get(key).asBoolean();
	}
	
	public Long getAsLong(String key) {
		return get(key).asLong();
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
				result.add(o.toString());
			}
		}
		return result;
	}

	@Override
	public Iterator<JSON> iterator() {
		if(isNull() || (!isArray() && !isObject())) {
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