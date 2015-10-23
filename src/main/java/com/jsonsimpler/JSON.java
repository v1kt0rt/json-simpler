package com.jsonsimpler;

import java.io.Reader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public final class JSON implements Iterable<JSON> {
	
	private static final Adapter ADAPTER = new Adapter();
	
	public static JSON from(String s) {
		return s==null ? new JSON() : new JSON(JSONValue.parse(s));
	}
	
	public static JSON from(Reader reader) {
		return new JSON(JSONValue.parse(reader));
	}
	
	public static JSON array(Object... values) {
		return new JSON().add(values);
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
		return new JSON(((JSONObject)obj).get(key));
	}
	
	public JSON get(int i) {
		if(obj==null) {
			return new JSON();
		}
		JSONArray array = (JSONArray)obj;
		if(array.size()<=i) {
			return new JSON();
		}
		return new JSON(array.get(i));
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
		JSONObject object = (JSONObject) obj;
		return (Boolean)object.get(key);
	}
	
	public Long getAsLong(String key) {
		return get(key).asLong();
	}
	
	public String toJSONString() {
		if(isObject()) {
			return ((JSONObject)obj).toJSONString();
		} else if(isArray()) {
			return ((JSONArray)obj).toJSONString();
		}
		return obj==null ? "null" : obj.toString();
	}
	
	public Set<String> keySet() {
		Set<String> result = new HashSet<>();
		if(isObject()) {
			for(Object o : ((JSONObject)obj).keySet()){
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
		return isArray() ?
			new ArrayIterator((JSONArray)obj) :
			new ArrayIterator((JSONArray)ADAPTER.createArrayInternal());
	}
	
	@SuppressWarnings("unchecked")
	public JSON put(String key, Object value) {
		while(value instanceof JSON) {
			value = ((JSON) value).obj;
		}
		if(obj==null) {
			obj = ADAPTER.createObjectInternal();
		}
		JSONObject json = (JSONObject) obj;
		json.put(key, value);
		return this;
	}
	
	public JSON putIf(boolean condition, String key, Object value) {
		return condition ? put(key, value) : this;
	}
	
	public JSON remove(String... keys) {
		if(obj==null) {
			return new JSON();
		}
		JSONObject json = (JSONObject) obj;
		for(String key : keys) {
			json.remove(key);
		}
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public JSON add(Object... values) {
		if(obj==null) {
			obj = ADAPTER.createArrayInternal();
		}
		for(Object value : values) {
			while(value instanceof JSON) {
				value = ((JSON) value).obj;
			}
			JSONArray json = (JSONArray) obj;
			json.add(value);
		}
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public JSON add(int index, Object value) {
		if(obj==null) {
			obj = ADAPTER.createArrayInternal();
		}
		while(value instanceof JSON) {
			value = ((JSON) value).obj;
		}
		JSONArray json = (JSONArray) obj;
		json.add(index, value);
		return this;
	}
	
	public int size() {
		if(isNull()) {
			return 0;
		}
		if(isArray()) {
			return ((JSONArray)obj).size();
		}
		if(isObject()) {
			return ((JSONObject)obj).size();
		}
		return 1;
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof JSON)) {
			return false;
		}
		JSON otherJson = (JSON) other;
		return Objects.equals(obj, otherJson.obj);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(obj);
	}
}