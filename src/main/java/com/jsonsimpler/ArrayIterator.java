package com.jsonsimpler;

import java.util.Iterator;

import org.json.simple.JSONArray;

final class ArrayIterator implements Iterator<JSON> {
	private final JSONArray array;
	private int i;
	
	public ArrayIterator(JSONArray array) {
		this.array = array;
	}
	
	@Override
	public boolean hasNext() {
		return i < array.size();
	}

	@Override
	public JSON next() {
		return new JSON(array.get(i++));
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}