package com.jsonsimpler;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class ObjectTest {
	
	@Test
	public void testStaticObject() {
		JSON j = JSON.object();
		Assert.assertFalse(j.isNull());
		Assert.assertTrue(j.isObject());
		j.put("a", "value");
		Assert.assertEquals("value", j.getAsString("a"));
	}
	
	@Test
	public void testGetAsBoolean() {
		JSON j = JSON.from("{\"x\":true}");
		Assert.assertTrue(j.getAsBoolean("x"));
		Assert.assertNull(j.getAsBoolean("y"));
	}
	
	@Test
	public void testGetAsLong() {
		JSON j = JSON.from("{\"x\":1234}");
		Assert.assertEquals((Long)1234l, j.getAsLong("x"));
	}
	
	@Test
	public void testGetAsStringArray() {
		JSON j = JSON.from("[\"a\", 1]");
		Assert.assertEquals("a", j.getAsString(0));
		Assert.assertNull(j.getAsString(2));
		try {
			j.getAsString(1);
			Assert.fail("Should throw ClassCastException.");
		} catch(ClassCastException ex) {
			//planned behavior
		}
	}
	
	@Test
	public void testGetAsStringObject() {
		JSON j = JSON.from("{\"aa\": \"a\",\"bb\":1}");
		Assert.assertEquals("a", j.getAsString("aa"));
		Assert.assertNull(j.getAsString("b"));
		try {
			j.getAsString("bb");
			Assert.fail("Should throw ClassCastException.");
		} catch(ClassCastException ex) {
			//planned behavior
		}
	}


	@Test
	public void testOverwrite() {
		JSON j = new JSON();
		j.put("a", "value");
		assertEquals("value", j.getAsString("a"));
		j.put("a", "value2");
		assertEquals("value2", j.getAsString("a"));
	}
	
	@Test
	public void testPutPrimitiveJSON() {
		JSON j = new JSON();
		j.put("a", new JSON("value"));
		assertTrue(j.get("a").isString());
		assertEquals("value", j.getAsString("a"));
		assertEquals(1, j.size());
	}
	
	@Test
	public void testPutCompoundJSON() {
		JSON root = new JSON();
		JSON sub = new JSON().put("b", "value");
		root.put("a", sub);
		
		JSON readSub = root.get("a");
		assertTrue(readSub.isObject());
		assertEquals("value", readSub.getAsString("b"));
	}
	
	@Test
	public void putIf() {
		JSON j = new JSON();
		j.putIf(true, "a", "value");
		j.putIf(false, "b", "value2");
		assertEquals("value", j.getAsString("a"));
		assertTrue(j.get("b").isNull());
	}
	
	@Test
	public void keySet() {
		JSON j = new JSON().put("a", "value").put("b", "value2");
		Set<String> set = j.keySet();
		assertEquals(2, set.size());
		assertTrue(set.contains("a"));
		assertTrue(set.contains("b"));
	}
	
	@Test
	public void testToJSONString() {
		JSON j1 = new JSON().put("a", "value").put("b", "value2");
		JSON j2 = JSON.from(j1.toJSONString());
		assertTrue(j1.equals(j2));
		assertTrue(j1.hashCode() == j2.hashCode());
	}
	
	@Test
	public void testNullKey() {
		JSON j = JSON.object().put(null, "value");
		assertEquals(1, j.keySet().size());
		assertEquals(null, j.keySet().iterator().next());
		assertEquals("value", j.getAsString(null));
	}
	
	@Test
	public void testValuesOfObject() {
		JSON j = JSON.object()
				.put("k1", "value")
				.put("k2", "value2");
		Iterator<JSON> iterator = j.values().iterator();
		assertEquals("value", iterator.next().asString());
		assertEquals("value2", iterator.next().asString());
	}
	
	@Test
	public void testValuesOfPrimitive() {
		JSON j = new JSON("value");
		Iterator<JSON> iterator = j.values().iterator();
		assertEquals("value", iterator.next().asString());
	}

}
