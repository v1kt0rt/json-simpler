package com.jsonsimpler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

public class JSONTest {
	
	@Test
	public void fromEmpty() {
		assertTrue(JSON.from((String)null).isNull());
		assertTrue(JSON.from("").isNull());
		assertTrue(JSON.from("   ").isNull());
	}
	
	@Test
	public void fromReader() {
		assertTrue(JSON.from(new StringReader("[]")).isArray());
	}
	
	@Test
	public void testEmpty() {
		JSON empty = new JSON(null);
		Assert.assertTrue(empty.isNull());
		Assert.assertFalse(empty.isArray());
		Assert.assertFalse(empty.isObject());
		Assert.assertFalse(empty.isString());
		Assert.assertNull(empty.asString());
		assertEquals("null", empty.toJSONString());
	}
	
	@Test
	public void testPrimitive() {
		JSON p = new JSON("a");
		assertEquals("a", p.toJSONString());
		assertEquals(1, p.size());
	}
	
	@Test
	public void testGetAsStringNullObject() {
		Assert.assertNull(new JSON().getAsString("b"));
	}
	
	@Test
	public void testEmptyIterator() {
		JSON j = new JSON();
		Iterator<JSON> i = j.iterator();
		Assert.assertFalse(i.hasNext());
	}
	
	@Test
	public void testSingleIterator() {
		JSON j = new JSON("aaa");
		Iterator<JSON> i = j.iterator();
		Assert.assertTrue(i.hasNext());
		Assert.assertEquals("aaa", i.next().asString());
		Assert.assertFalse(i.hasNext());
		try {
			i.next();
			Assert.fail("Should throw IllegalStateException");
		} catch(IllegalStateException ex) {
			//expected
		}
	}
	
	@Test
	public void testInvalidGet() {
		JSON j = new JSON("string");
		Assert.assertTrue(j.get("key").isNull());
		Assert.assertNull(j.getAsString("key"));
	}
	
	@Test
	public void testRemove() {
		JSON j = new JSON().put("key", "value");
		j.remove("key");
		Assert.assertTrue(j.get("key").isNull());
	}
	
	@Test
	public void testRemoveNotExisting() {
		JSON j = new JSON().put("key", "value");
		j.remove("key2");
		Assert.assertFalse(j.get("key").isNull());
	}
	
	@Test
	public void testRemoveFromEmpty() {
		JSON j = new JSON();
		j.remove("key2");
		Assert.assertTrue(j.isNull());
	}
	
	@Test
	public void notEquals() {
		assertFalse(new JSON("a").equals("a"));
	}
}
