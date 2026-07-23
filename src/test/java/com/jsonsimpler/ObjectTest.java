package com.jsonsimpler;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Iterator;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class ObjectTest {
	
	@Test
	public void testStaticObject() {
		JSON j = JSON.object();
		assertFalse(j.isNull());
		assertTrue(j.isObject());
		j.put("a", "value");
		assertEquals("value", j.getAsString("a"));
	}
	
	@Test
	public void testGetAsBoolean() {
		JSON j = JSON.from("{\"x\":true}");
		assertTrue(j.getAsBoolean("x"));
		assertNull(j.getAsBoolean("y"));
	}
	
	@Test
	public void testGetAsLong() {
		JSON j = JSON.from("{\"x\":1234}");
		assertEquals((Long)1234L, j.getAsLong("x"));
	}
	
	@Test
	public void testGetAsStringArray() {
		JSON j = JSON.from("[\"a\", 1]");
		assertEquals("a", j.getAsString(0));
		assertNull(j.getAsString(2));
		try {
			j.getAsString(1);
			fail("Should throw ClassCastException.");
		} catch(ClassCastException ex) {
			//planned behavior
		}
	}
	
	@Test
	public void testGetAsStringObject() {
		JSON j = JSON.from("{\"aa\": \"a\",\"bb\":1}");
		assertEquals("a", j.getAsString("aa"));
		assertNull(j.getAsString("b"));
		try {
			j.getAsString("bb");
			fail("Should throw ClassCastException.");
		} catch(ClassCastException ex) {
			//planned behavior
		}
	}

	@Test
	public void testOverwrite() {
		JSON j = JSON.object();
		j.put("a", "value");
		assertEquals("value", j.getAsString("a"));
		j.put("a", "value2");
		assertEquals("value2", j.getAsString("a"));
	}
	
	@Test
	public void testPutPrimitiveJSON() {
		JSON j = JSON.object();
		j.put("a", new JSON("value"));
		assertTrue(j.get("a").isString());
		assertEquals("value", j.getAsString("a"));
		assertEquals(1, j.size());
	}
	
	@Test
	public void testPutCompoundJSON() {
		JSON root = JSON.object();
		JSON sub = JSON.object().put("b", "value");
		root.put("a", sub);
		
		JSON readSub = root.get("a");
		assertTrue(readSub.isObject());
		assertEquals("value", readSub.getAsString("b"));
	}
	
	@Test
	public void putIf() {
		JSON j = JSON.object();
		j.putIf(true, "a", "value");
		j.putIf(false, "b", "value2");
		assertEquals("value", j.getAsString("a"));
		assertTrue(j.get("b").isNull());
	}
	
	@Test
	public void keySet() {
		JSON j = JSON.object().put("a", "value").put("b", "value2");
		Set<String> set = j.keySet();
		assertEquals(2, set.size());
		assertTrue(set.contains("a"));
		assertTrue(set.contains("b"));
	}
	
	@Test
	public void testToJSONString() {
		JSON j1 = JSON.object().put("a", "value").put("b", "value2");
		JSON j2 = JSON.from(j1.toJSONString());
		assertEquals(j1, j2);
		assertEquals(j1.hashCode(), j2.hashCode());
	}
	
	@Test
	public void testNullKey() {
		JSON j = JSON.object().put(null, "value");
		assertEquals(1, j.keySet().size());
		assertNull(j.keySet().iterator().next());
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
		assertFalse(iterator.hasNext());
	}
	
	@Test
	public void testValuesOfPrimitive() {
		JSON j = new JSON("value");
		Iterator<JSON> iterator = j.values().iterator();
		assertEquals("value", iterator.next().asString());
		assertFalse(iterator.hasNext());
	}
	
	@Test
	public void testValuesOfNull() {
		JSON j = new JSON();
		Iterator<JSON> iterator = j.values().iterator();
		assertFalse(iterator.hasNext());
	}
	
	@Test
	public void testValuesOfArray() {
		JSON j = JSON.array("value", "value2");
		Iterator<JSON> iterator = j.values().iterator();
		assertEquals("value", iterator.next().asString());
		assertEquals("value2", iterator.next().asString());
		assertFalse(iterator.hasNext());
	}
}
