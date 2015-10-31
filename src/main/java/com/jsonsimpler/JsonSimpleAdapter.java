package com.jsonsimpler;

import java.io.Reader;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

class JsonSimpleAdapter implements Adapter {
	
	/* (non-Javadoc)
	 * @see com.jsonsimpler.IAdapter#parse(java.io.Reader)
	 */
	@Override
	public Object parse(Reader reader) {
		return JSONValue.parse(reader);
	}
	
	/* (non-Javadoc)
	 * @see com.jsonsimpler.IAdapter#isObject(java.lang.Object)
	 */
	@Override
	public boolean isObject(Object obj) {
		return obj instanceof JSONObject;
	}
	
	/* (non-Javadoc)
	 * @see com.jsonsimpler.IAdapter#createObjectInternal()
	 */
	@Override
	public Object createObjectInternal() {
		return new JSONObject();
	}
	
	/* (non-Javadoc)
	 * @see com.jsonsimpler.IAdapter#getFromObject(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object getFromObject(Object obj, String key) {
		return ((JSONObject)obj).get(key);
	}
	
	/* (non-Javadoc)
	 * @see com.jsonsimpler.IAdapter#sizeOfObject(java.lang.Object)
	 */
	@Override
	public int sizeOfObject(Object obj) {
		return ((JSONObject)obj).size();
	}
	
	/* (non-Javadoc)
	 * @see com.jsonsimpler.IAdapter#putIntoObject(java.lang.Object, java.lang.String, java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void putIntoObject(Object obj, String key, Object value) {
		((JSONObject) obj).put(key, value);
	}
	
	/* (non-Javadoc)
	 * @see com.jsonsimpler.IAdapter#removeFromObject(java.lang.Object, java.lang.String)
	 */
	@Override
	public void removeFromObject(Object obj, String key) {
		((JSONObject) obj).remove(key);
	}
	
	/* (non-Javadoc)
	 * @see com.jsonsimpler.IAdapter#keySet(java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Set<Object> keySet(Object obj) {
		return ((JSONObject)obj).keySet();
	}
	
	/* (non-Javadoc)
	 * @see com.jsonsimpler.IAdapter#objectToJSONString(java.lang.Object)
	 */
	@Override
	public String objectToJSONString(Object obj) {
		return ((JSONObject) obj).toJSONString();
	}
	
	/* (non-Javadoc)
	 * @see com.jsonsimpler.IAdapter#isArray(java.lang.Object)
	 */
	@Override
	public boolean isArray(Object obj) {
		return obj instanceof JSONArray;
	}
	
	/* (non-Javadoc)
	 * @see com.jsonsimpler.IAdapter#createArrayInternal()
	 */
	@Override
	public Object createArrayInternal() {
		return new JSONArray();
	}
	
	/* (non-Javadoc)
	 * @see com.jsonsimpler.IAdapter#sizeOfArray(java.lang.Object)
	 */
	@Override
	public int sizeOfArray(Object obj) {
		return ((JSONArray)obj).size();
	}
	
	/* (non-Javadoc)
	 * @see com.jsonsimpler.IAdapter#getFromArray(java.lang.Object, int)
	 */
	@Override
	public Object getFromArray(Object array, int i) {
		return ((JSONArray)array).get(i);
	}
	
	/* (non-Javadoc)
	 * @see com.jsonsimpler.IAdapter#arrayToJSONString(java.lang.Object)
	 */
	@Override
	public String arrayToJSONString(Object obj) {
		return ((JSONArray) obj).toJSONString();
	}
	
	/* (non-Javadoc)
	 * @see com.jsonsimpler.IAdapter#addToArray(java.lang.Object, java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void addToArray(Object obj, Object value) {
		((JSONArray)obj).add(value);
	}
	
	/* (non-Javadoc)
	 * @see com.jsonsimpler.IAdapter#addToArray(java.lang.Object, java.lang.Object, int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void addToArray(Object obj, Object value, int index) {
		((JSONArray)obj).add(index, value);
	}
}
