package com.jsonsimpler;

import java.util.Iterator;

import junit.framework.TestCase;

public class JSONTest extends TestCase {
	
	public void testEmpty() {
		JSON empty = new JSON(null);
		TestCase.assertTrue(empty.isNull());
		TestCase.assertFalse(empty.isArray());
		TestCase.assertFalse(empty.isObject());
		TestCase.assertFalse(empty.isString());
		TestCase.assertNull(empty.asString());
		TestCase.assertNull(empty.getRawObject());
	}
	
	public void testGetAsBoolean() {
		JSON j = JSON.from("{\"x\":true}");
		TestCase.assertTrue(j.getAsBoolean("x"));
		TestCase.assertNull(j.getAsBoolean("y"));
	}
	
	public void testGetAsLong() {
		JSON j = JSON.from("{\"x\":1234}");
		TestCase.assertEquals((Long)1234l, j.getAsLong("x"));
	}
	
	public void testGetAsStringArray() {
		JSON j = JSON.from("[\"a\", 1]");
		TestCase.assertEquals("a", j.getAsString(0));
		TestCase.assertNull(j.getAsString(2));
		try {
			j.getAsString(1);
			TestCase.fail("Should throw ClassCastException.");
		} catch(ClassCastException ex) {
			//planned behavior
		}
	}
	
	public void testGetAsStringObject() {
		JSON j = JSON.from("{\"aa\": \"a\",\"bb\":1}");
		TestCase.assertEquals("a", j.getAsString("aa"));
		TestCase.assertNull(j.getAsString("b"));
		try {
			j.getAsString("bb");
			TestCase.fail("Should throw ClassCastException.");
		} catch(ClassCastException ex) {
			//planned behavior
		}
	}
	
	public void testGetAsStringNullObject() {
		TestCase.assertNull(new JSON().getAsString("b"));
	}
	
	public void testAdd() {
		JSON a = new JSON();
		a.add("a");
		TestCase.assertEquals("a", a.getAsString(0));
		a.add(new JSON("b"));
		TestCase.assertEquals("b", a.getAsString(1));
		a.add(new JSON().add("c"));
		TestCase.assertTrue(a.get(2).isArray());
		TestCase.assertEquals("c", a.get(2).getAsString(0));
	}
	
	public void testEmptyterator() {
		JSON j = new JSON();
		Iterator<JSON> i = j.iterator();
		TestCase.assertFalse(i.hasNext());
	}
	
	public void testSingleIterator() {
		JSON j = new JSON("aaa");
		Iterator<JSON> i = j.iterator();
		TestCase.assertTrue(i.hasNext());
		TestCase.assertEquals("aaa", i.next().asString());
		TestCase.assertFalse(i.hasNext());
	}
	
	public void testInvalidGet() {
		JSON j = new JSON("string");
		TestCase.assertTrue(j.get("key").isNull());
		TestCase.assertNull(j.getAsString("key"));
	}

}
