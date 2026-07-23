package com.jsonsimpler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ArrayTest {

	@Test
	public void test() {
		JSON emptyArray = JSON.from("[]");
		assertTrue(emptyArray.isArray());
	}
	
	@Test
	public void nullArray() {
		JSON nil = new JSON();
		assertTrue(nil.isNull());
		assertTrue(nil.get(1).isNull());
		assertEquals(0, nil.size());
	}
	
	@Test
	public void emptyArray() {
		JSON empty = JSON.array();
		assertFalse(empty.isNull());
		assertTrue(empty.isArray());
		assertEquals(0, empty.size());
	}
	
	@Test
	public void simpleArray() {
		JSON array = JSON.array("1", "2", "3");
		assertTrue(array.isArray());
		assertEquals("1", array.getAsString(0));
		assertEquals("2", array.getAsString(1));
		assertEquals("3", array.getAsString(2));
		assertEquals(3, array.size());
	}
	
	@Test
	public void testAdd() {
		JSON a = JSON.array();
		a.add("a");
		assertEquals("a", a.getAsString(0));
		a.add(new JSON("b"));
		assertEquals("b", a.getAsString(1));
		a.add(new JSON().add("c"));
		assertTrue(a.get(2).isArray());
		assertEquals("c", a.get(2).getAsString(0));
	}
	
	@Test
	public void testAddNull() {
		JSON a = JSON.array();
		a.add("a");
		a.add(new Object[] { null });
		a.add("b");
		assertTrue(a.get(1).isNull());
		assertEquals("[\"a\",null,\"b\"]", a.toJSONString());
		assertEquals("b", a.get(2).asString());
		
		JSON b = JSON.from(a.toJSONString());
		assertTrue(b.get(1).isNull());
		assertEquals("b", b.get(2).asString());
	}
	
	@Test
	public void keySetIsEmpty() {
		JSON a = JSON.array(1, 2, 3);
		assertTrue(a.keySet().isEmpty());
	}
	
	@Test
	public void iterator() {
		int ctr = 0;
		String[] expected = new String[] {"a", "b", "c" };
		for(JSON item : JSON.array("a", "b", "c")) {
			assertEquals(expected[ctr++], item.asString());
		}
	}
	
	
	@Test
	public void testToJSONString() {
		JSON j1 = JSON.array("a", "b", "c");
		JSON j2 = JSON.from(j1.toJSONString());
		assertEquals(j1, j2);
		assertEquals(j1.hashCode(), j2.hashCode());
	}

}
